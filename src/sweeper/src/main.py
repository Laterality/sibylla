import crawler_joongang
import crawler_chosun
import crawler_donga
import util

crawler_joongang.crawl()
# crawler_chosun.crawl()
# crawler_donga.crawl()

# After crawling finished,
# Send webhook to prophet to train with crawled articles
util.send_webhook()
