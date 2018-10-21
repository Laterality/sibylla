import threading
import flask
import util
import train as tr

app = flask.Flask(__name__)
model = util.load_model()


class TrainThread(threading.Thread):

    def __init__(self, model, docs, labels):
        threading.Thread.__init__(self)
        self.docs = docs
        self.labels = labels
        self.model = model

    def run(self):
        tr.train(model, self.docs, self.labels)


@app.route("/get-similarity", methods=["GET"])
def get_similarity():
    article_1 = flask.request.args.get("article1")
    article_2 = flask.request.args.get("article2")
    similarity = model.docvecs.similarity(int(article_1), int(article_2))
    return flask.jsonify(
        result="ok",
        similarity=str(similarity)
    )


@app.route("/train", methods=["GET"])
def train():
    docs, ids = util.fetch_untrained_data()

    TrainThread(model, docs, ids).start()

    return flask.jsonify(
        result="ok"
    )
