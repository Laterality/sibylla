from selenium import webdriver as wd
from datetime import datetime
import re

from Article import Article

def extract_news(driver, url):
    # extract to Article instance

    # url에서 uid 추출
    pattern = re.compile("/[0-9]+.html")
    uid = pattern.search(url).group().split(".")[0].split("/")[1]

    driver.get(url)
    title = driver.find_element_by_id("news_title_text_id").text
    date_text = driver.find_element_by_class_name("news_date").text
    paragraphs = driver.find_elements_by_class_name("par")
    written_date = datetime.now()

    paragraph_texts = []

    for p in paragraphs:
        paragraph_texts.append(p.text)

    content = "\n\n".join(paragraph_texts)

    # ex. "입력 2018.09.18 03:01"
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


main_url = "http://www.chosun.com/"
link_list = []

driver = wd.Chrome(executable_path="../webdriver/chromedriver.exe")
driver.implicitly_wait(10)
driver.get(main_url)

article_link_patterns = [
    "news.chosun.com/site/data/html_dir/"
]

hrefElms = driver.find_elements_by_css_selector("[href]")
print("%d elms found" % len(hrefElms))

for i in hrefElms:
    href = i.get_attribute("href")
    for p in article_link_patterns:
        if p in href:
            link_list.append(href)
            break

print("%d articles found" % len(link_list))

# print(link_list[0])
# print(extract_news(driver, link_list[0]))
print(extract_news(driver, "http://news.chosun.com/site/data/html_dir/2018/09/18/2018091800055.html"))
