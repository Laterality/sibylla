from selenium import webdriver as wd
from datetime import datetime

from Article import Article


# 기사 본문의 url을 받아서 Article 클래스로 추출하는 함수
def extract(driver, url):
    uid = url.split("/")[-1]
    driver.get(url)
    driver.execute_script("document.querySelectorAll(\".ab_photo\").forEach(obj => obj.remove());")
    title = driver.find_element_by_id("article_title").text
    content = driver.find_element_by_id("article_body").text
    dateText = driver.find_elements_by_css_selector(".byline em")[1].text
    dateTokens = dateText.split(" ")
    ymd = dateTokens[1].split(".")
    time = dateTokens[2].split(":")
    written_date = datetime.now()
    written_date = written_date.replace(year=int(ymd[0]),
                                        month=int(ymd[1]),
                                        day=int(ymd[2]),
                                        hour=int(time[0]),
                                        minute=int(time[1]),
                                        second=0,
                                        microsecond=0)

    return Article(
        uid=uid,
        title=title,
        content=content,
        written_date=written_date,
        url=url
    )



main_url = "https://joongang.joins.com/"
exclude_urls = []
link_list = []

exclude_urls.append("https://innovationlab.co.kr/")

driver = wd.Chrome(executable_path="../webdriver/chromedriver.exe")
driver.implicitly_wait(10)

driver.get(main_url)
main_articles = driver.find_elements_by_css_selector(".main_article .bd ul li span.thumb a")

# 링크 정제 과정
for i in main_articles:
    href = i.get_attribute("href")

    if "?" in href:
        href = href.split("?")[0]

    # 기사가 아닌 링크는 제거
    for excl in exclude_urls:
        if excl in href:
            href = None
            break

    if href is not None:
        link_list.append(href)

print("%d articles found" % len(link_list))

print(extract(driver=driver, url=link_list[0]))
