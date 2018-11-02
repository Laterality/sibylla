import flask
import threading

import crawler_joongang
import crawler_chosun
import crawler_donga
import util

app = flask.Flask(__name__)


class CrawlingThread(threading.Thread):

    def run(self):
        crawler_joongang.crawl()
        # crawler_chosun.crawl()
        # crawler_donga.crawl()

        # After crawling finished,
        # Send webhook to prophet to train with crawled articles
        util.send_webhook()


@app.route("/crawl")
def crawl():
    CrawlingThread().start()

    return flask.jsonify(
        result="ok"
    )



