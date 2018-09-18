from selenium import webdriver as wd
from datetime import datetime
import re

from Article import Article

def extract_news(driver, url):
    # extract to Article instance

    # url에서 uid 추출
    p = re.compile("/[0-9]+.html")
    uid = p.search(url).group().split(".")[0].split("/")[1]

    driver.get(url)
    title = driver.find_element_by_id("news_title_text_id").text
    dateText = driver.find_element_by_class_name("news_date").text
    paragraphs = driver.find_elements_by_class_name("par")
    written_date = datetime.now()

    pTexts = []

    for p in paragraphs:
        pTexts.append(p.text)

    content = "\n\n".join(pTexts)

    # ex. "입력 2018.09.18 03:01"
    dateTokens = dateText.split("|")[0].split(" ")
    date = dateTokens[1].split(".")
    time = dateTokens[2].split(":")

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



def extract_biz(driver, url):
    # extract to Article instance
    a = 1


main_url = "http://www.chosun.com/"
include_urls = []
link_list = []

include_urls.append("news.chosun.com")

driver = wd.Chrome(executable_path="../webdriver/chromedriver.exe")
driver.implicitly_wait(10)
driver.get(main_url)

article_link_pattern = "chosun.com/site/data/html_dir/"

hrefElms = driver.find_elements_by_css_selector("[href]")
print("%d elms found" % len(hrefElms))

for i in hrefElms:
    href = i.get_attribute("href")
    if article_link_pattern in href:
        link_list.append(href)

print("%d articles found" % len(link_list))

# print(link_list[0])
# print(extract_news(driver, link_list[0]))
print(extract_news(driver, "http://news.chosun.com/site/data/html_dir/2018/09/18/2018091800055.html"))
