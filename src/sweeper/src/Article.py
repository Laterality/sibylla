class Article:

    def __init__(self, url, uid, title, content, written_date, images):
        self.url = url
        self.uid = uid
        self.title = title
        self.content = content
        self.written_date = written_date
        self.images = images

    def __str__(self):
        return "url: %s\nuid: %s\ntitle: %s\ncontent length: %d\nwritten_date: %s\nimages: %s" % \
               (self.url, self.uid, self.title, len(self.content), self.written_date, self.images)
