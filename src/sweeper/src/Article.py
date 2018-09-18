class Article:

    def __init__(self, url, uid, title, content, written_date):
        self.url = url
        self.uid = uid
        self.title = title
        self.content = content
        self.written_date = written_date

    def __str__(self):
        return "url: %s\nuid: %s\ntitle: %s\ncontent length: %d\nwritten_date: %s" % \
               (self.url, self.uid, self.title, len(self.content), self.written_date)
