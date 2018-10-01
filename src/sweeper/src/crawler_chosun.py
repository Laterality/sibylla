from selenium.common.exceptions import *
from selenium import webdriver as wd
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from datetime import datetime
import re

from Article import Article
import util


def extract_news(driver, url):
    # extract to Article instance
    PATIENCE = 15

    # url에서 uid 추출
    pattern = re.compile("/[0-9]+.html")
    uid = pattern.search(url).group().split(".")[0].split("/")[1]
    try:
        driver.get(url)
        # title = driver.find_element_by_id("news_title_text_id").text
        title = WebDriverWait(driver, PATIENCE) \
            .until(EC.presence_of_element_located((By.ID, "news_title_text_id"))) \
            .text
        # date_text = driver.find_element_by_class_name("news_date").text
        date_text = WebDriverWait(driver, PATIENCE) \
            .until(EC.presence_of_element_located((By.CLASS_NAME, "news_date"))) \
            .text

        # paragraphs = driver.find_elements_by_class_name("par")
        paragraphs = WebDriverWait(driver, PATIENCE) \
            .until(EC.presence_of_all_elements_located((By.CLASS_NAME, "par")))
        written_date = datetime.now()

        paragraph_texts = []

        for p in paragraphs:
            paragraph_texts.append(p.text)

        content = "\n\n".join(paragraph_texts)

        date_tokens = date_text.split("|")[0].split(" ")
        date = date_tokens[1].split(".")
        time = date_tokens[2].split(":")

        written_date = written_date.replace(
            year=int(date[0]),
            month=int(date[1]),
            day=int(date[2]),
            hour=int(time[0]),
            minute=int(time[1]),
            second=0,
            microsecond=0
        )

        return Article(uid=uid,
                       url=url,
                       title=title,
                       content=content,
                       written_date=written_date)

    finally:
        return None


def crawl():
    PATIENCE = 15
    MAX_RETRY = 3
    SOURCE_NAME = "조선일보"
    MAIN_URL = "http://www.chosun.com/"

    driver = util.get_driver()
    driver.get(MAIN_URL)
    driver.set_page_load_timeout(PATIENCE)
    article_link_patterns = [
        "news.chosun.com/site/data/html_dir/"
    ]
    link_list = []
    timeout_cnt = 0
    skipped_cnt = 0

    # href_elms = driver.find_elements_by_class_name("sec_con")[1].find_elements_by_css_selector("[href]")
    href_elms = WebDriverWait(driver, PATIENCE) \
        .until(EC.presence_of_all_elements_located((By.CLASS_NAME, "sec_con")))[1] \
        .find_elements_by_css_selector("[href]")
    # live_elms = driver.find_elements_by_css_selector("#today_live_con_id [href]")
    live_elms = WebDriverWait(driver, PATIENCE) \
        .until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, "#today_live_con_id [href]")))
    href_elms += live_elms

    for i in href_elms:
        href = i.get_attribute("href")
        for p in article_link_patterns:
            if p in href:
                try:
                    link_list.index(href)
                except ValueError:
                    link_list.append(href)
                break

    print("%d articles found" % len(link_list))

    for i in link_list:
        # Retry loop
        for retry in range(0, 3):
            try:
                article = extract_news(driver, i)
                if article is not None:
                    util.post(article, SOURCE_NAME)
                    break
                else:
                    continue
            except (TimeoutException, NoSuchElementException, StaleElementReferenceException):
                if retry == MAX_RETRY - 1:
                    skipped_cnt += 1
                else:
                    driver.refresh()
                    timeout_cnt += 1

    driver.quit()

    print("Done with %d timeouts and %d skipped pages in %d links" % (timeout_cnt, skipped_cnt, len(link_list)))
