from selenium import webdriver as wd
import requests
import json


def get_driver():
    DRIVER_PATH = "../src/webdriver/chromedriver"

    options = wd.ChromeOptions()
    options.add_argument("headless")
    options.add_argument("--disable-dev-shm-usage")
    return wd.Chrome(executable_path=DRIVER_PATH, options=options)


def post(article, source_name):
    REST_URL = "http://localhost:8080"

    data = {
        "title": article.title,
        "content": article.content,
        "url": article.url,
        "uid": article.uid,
        "writtenDate": round(article.written_date.timestamp() * 1000),
        "sourceName": source_name
    }
    headers = {
        "Content-Type": "application/json; charset=utf-8"
    }
    res = requests.post(url=REST_URL, data=json.dumps(data), headers=headers)

    if res.status_code != 201:
        print("Request not handled, url: %s" % article.url)
