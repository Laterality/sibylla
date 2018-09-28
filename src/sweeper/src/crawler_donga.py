from selenium.common.exceptions import *
from selenium import webdriver as wd
from datetime import datetime
import re

from Article import Article
import util


def extract(driver, url):
    driver.get(url)

    uid_pattern = re.compile("/[0-9]+/[0-9]+/")
    uid = uid_pattern.search(string=url).group().split("/")[2]

    # 이미지, 본문과 관련 없는 텍스트 제거
    driver.execute_script("document.querySelectorAll(\".articlePhotoC\").forEach(e => e.remove())")
    driver.execute_script("document.querySelectorAll(\".article_issue\").forEach(e => e.remove())")
    driver.execute_script("document.querySelectorAll(\".article_relation\").forEach(e => e.remove())")

    date_tokens = driver.find_element_by_class_name("date01").text.split(" ")
    title = driver.find_element_by_css_selector("h2.title").text
    content = driver.find_element_by_class_name("article_txt").text

    date = date_tokens[1].split("-")
    time = date_tokens[2].split(":")

    written_date = datetime.now()
    written_date = written_date.replace(
        year=int(date[0]),
        month=int(date[1]),
        day=int(date[2]),
        hour=int(time[0]),
        minute=int(time[1]),
        second=0,
        microsecond=0
    )

    return Article(
        url=url,
        uid=uid,
        title=title,
        content=content,
        written_date=written_date
    )


def crawl():
    PATIENCE = 15
    MAX_RETRY = 3
    SOURCE_NAME = "동아일보"
    MAIN_URL = "http://www.donga.com/"

    driver = util.get_driver()
    driver.set_page_load_timeout(PATIENCE)
    driver.get(MAIN_URL)

    INCLUDE_URLS = [
        "news.donga.com/Main",
        "news.donga.com/MainTop"
    ]
    article_links = []
    timeout_cnt = 0
    skipped_cnt = 0

    href_elms = driver.find_elements_by_css_selector("[href]")

    for e in href_elms:
        href = e.get_attribute("href")
        for i in INCLUDE_URLS:
            if i in href:
                article_links.append(href)
                break

    print("%d articles found" % len(article_links))

    for i in article_links[:10]:
        for retry in range(0, MAX_RETRY):
            try:
                article = extract(driver, i)
                util.post(article, SOURCE_NAME)
                break
            except (TimeoutException, NoSuchElementException, StaleElementReferenceException):
                if retry == MAX_RETRY - 1:
                    skipped_cnt += 1
                else:
                    driver.refresh()
                    timeout_cnt += 1

    driver.quit()

    print("Done with %d timeouts and %d skipped pages in %d links" % (timeout_cnt, skipped_cnt, len(article_links)))
