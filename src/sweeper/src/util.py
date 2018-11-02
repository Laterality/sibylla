from selenium import webdriver as wd
import requests
import json
import os
import sys


def get_driver():
    DRIVER_PATH = os.environ.get("CHROME_DRIVER_PATH")
    # DRIVER_PATH = "../webdriver/chromedriver.exe"

    if DRIVER_PATH is None:
        print("Invalid chrome driver path: %s" % DRIVER_PATH)
        sys.exit(1)

    options = wd.ChromeOptions()
    options.add_argument("headless")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")
    return wd.Chrome(executable_path=DRIVER_PATH, options=options)


def post(article, source_name):
    REST_URL = "http://altair.latera.kr/sb/api/article"

    data = {
        "title": article.title,
        "content": article.content,
        "url": article.url,
        "uid": article.uid,
        "writtenDate": round(article.written_date.timestamp() * 1000),
        "sourceName": source_name,
        "images": article.images
    }
    headers = {
        "Content-Type": "application/json; charset=utf-8"
    }

    res = requests.post(url=REST_URL, data=json.dumps(data), headers=headers)

    if res.status_code != 201:
        print("Request not handled, url: %s" % article.url)


def send_webhook():
    REST_URL = "http://altair.latera.kr/sb/api/prophet"

    res = requests.get(url=REST_URL + "/train")
    if res.status_code != 200:
        print("Webhook failed")
