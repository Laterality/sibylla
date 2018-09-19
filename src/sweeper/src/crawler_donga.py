from selenium import webdriver as wd
from datetime import datetime
import re

from Article import Article


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


main_url = "http://www.donga.com/"
driver = wd.Chrome(executable_path="../webdriver/chromedriver.exe")

driver.implicitly_wait(10)
driver.get(main_url)

include_urls = [
    "news.donga.com/Main",
    "news.donga.com/MainTop"
]
article_links = []

href_elms = driver.find_elements_by_css_selector("[href]")

for e in href_elms:
    href = e.get_attribute("href")
    for i in include_urls:
        if i in href:
            article_links.append(href)
            break

print("%d articles found" % len(article_links))
for i in article_links:
    print(i)

print(extract(driver, article_links[0]))
