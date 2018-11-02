import threading
import flask
import util
import train as tr

app = flask.Flask(__name__)
model = util.load_model()
new_model = None
should_model_change = False

if model is None:
    print("doc2vec model is not loaded")


class TrainThread(threading.Thread):

    def __init__(self, docs, labels):
        threading.Thread.__init__(self)
        self.docs = docs
        self.labels = labels
        self.model = util.create_model()

    def run(self):
        tr.train(model, self.docs, self.labels)
        util.set_trained(self.labels)
        should_model_change = True


def check_model():
    if should_model_change:
        model = new_model
        new_model = None
        should_model_change = False


@app.route("/get-similarity", methods=["GET"])
def get_similarity():
    article_1 = flask.request.args.get("article1")
    article_2 = flask.request.args.get("article2")

    article1 = util.fetch(article_1)
    article2 = util.fetch(article_2)
    tokens = util.tokenize([article1[1], article2[1]])
    similarity = str(model.docvecs.similarity_unseen_docs(model, tokens[0], tokens[1]))

    check_model()

    return flask.jsonify(
        result="ok",
        similarity=str(similarity)
    )


@app.route("/get-similarities", methods=["GET"])
def get_similarities():
    comparison = flask.request.args.get("article")
    ctoken = util.tokenize([util.fetch(comparison)[1]])

    recent_articles = util.fetch_top_100()
    tokens = util.tokenize([a[1] for a in recent_articles])

    similarities = [str(model.docvecs.similarity_unseen_docs(model, ctoken[0], token))
                    for token in tokens]

    check_model()

    return flask.jsonify(
        result="ok",
        articleIds=[a[0] for a in recent_articles],
        similarities=similarities
    )


@app.route("/train", methods=["GET"])
def train():
    docs, ids = util.fetch_untrained_data()

    TrainThread(docs, ids).start()

    check_model()

    return flask.jsonify(
        result="ok"
    )
