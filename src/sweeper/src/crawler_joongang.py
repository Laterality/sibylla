
from selenium.common.exceptions import *
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from datetime import datetime
import sys

from Article import Article
import util


# 기사 본문의 url을 받아서 Article 클래스로 추출하는 함수
def extract(driver, url):
    title_exclusion_strs = [
        "[시가 있는 아침]",
        "[바로잡습니다]",
        "[알림]"
    ]

    uid = url.split("/")[-1]
    driver.get(url)

    image_elms = driver.find_elements_by_css_selector("#content .image img")
    images = []

    for i in image_elms:
        images.append(i.get_attribute("src"))

    # 이미지 추출 후 이미지 엘리먼트 제거
    driver.execute_script("document.querySelectorAll(\".ab_photo\").forEach(obj => obj.remove());")
    driver.execute_script("document.querySelectorAll(\".ab_subtitle\").forEach(obj => obj.remove());")
    title = driver.find_element_by_id("article_title").text
    content = driver.find_element_by_id("article_body").text
    date_text = driver.find_elements_by_css_selector(".byline em")[1].text
    date_tokens = date_text.split(" ")
    ymd = date_tokens[1].split(".")
    time = date_tokens[2].split(":")
    written_date = datetime.now()
    written_date = written_date.replace(year=int(ymd[0]),
                                        month=int(ymd[1]),
                                        day=int(ymd[2]),
                                        hour=int(time[0]),
                                        minute=int(time[1]),
                                        second=0,
                                        microsecond=0)

    for i in title_exclusion_strs:
        if i in title:
            return None

    return Article(
        uid=uid,
        title=title,
        content=content,
        written_date=written_date,
        url=url,
        images=images
    )


def crawl():
    PATIENCE = 15
    MAX_RETRY = 3
    SOURCE_NAME = "중앙일보"
    MAIN_URL = "https://joongang.joins.com/"
    INCLUDE_URLS = [
        "news.joins.com/article"
    ]
    inclusion_filtered = []
    link_list = []
    timeout_cnt = 0
    skipped_cnt = 0

    driver = util.get_driver()
    driver.set_page_load_timeout(PATIENCE)

    done = False
    for r in range(0, PATIENCE):
        try:
            driver.get(MAIN_URL)
            done=True
            # href_elms = driver.find_elements_by_css_selector("[href]")
            href_elms = WebDriverWait(driver, PATIENCE) \
                .until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, "[href]")))
        except TimeoutException:
            continue

    if not done:
        driver.quit()
        sys.exit(1)

    for i in href_elms:
        href = i.get_attribute("href")

        for j in INCLUDE_URLS:
            if j in href:
                inclusion_filtered.append(href)
                break

    # 링크 정제 과정
    for href in inclusion_filtered:

        if "?" in href:
            href = href.split("?")[0]

        if href is not None:
            try:
                link_list.index(href)
            except ValueError:
                # 추가할 링크가 리스트에 없는 경우 => 중복되지 않는 경우
                link_list.append(href)

    print("%d articles found" % len(link_list))

    for i in link_list:
        for retry in range(0, MAX_RETRY):
            try:
                article = extract(driver=driver, url=i)
                if article is not None:
                    util.post(article, SOURCE_NAME)
                break
            except (TimeoutException, NoSuchElementException, StaleElementReferenceException):
                if retry == MAX_RETRY - 1:
                    skipped_cnt += 1
                else:
                    try:
                        driver.refresh()
                    except TimeoutException:
                        skipped_cnt += 1
                        continue
                    timeout_cnt += 1

    driver.quit()
    print("Done with %d timeouts and %d skipped pages in %d links" % (timeout_cnt, skipped_cnt, len(link_list)))
