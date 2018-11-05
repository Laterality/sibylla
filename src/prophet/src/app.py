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
        global should_model_change

        tr.train(model, self.docs, self.labels)
        util.set_trained(self.labels)
        should_model_change = True


def check_model():
    global should_model_change
    global model
    global new_model

    if should_model_change:
        model = new_model
        new_model = None
        should_model_change = False


@app.route("/get-similarity", methods=["GET"])
def get_similarity():
    global model

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
    global model

    comparison = int(flask.request.args.get("article"))
    ctoken = util.tokenize([util.fetch(comparison)[1]])

    recent_articles = util.fetch_top_100(comparison)

    similarities = []
    for a in recent_articles:
        sim = util.fetch_similarity(min(comparison, a[0]), max(comparison, a[0]))
        if sim is None:
            sim = model.docvecs.similarity_unseen_docs(model, ctoken[0], util.tokenize([a[1]])[0])
            util.insert_similarity(comparison, a[0], sim.item())
        similarities.append(str(sim))

    check_model()

    return flask.jsonify(
        result="ok",
        articleIds=[a[0] for a in recent_articles],
        similarities=similarities
    )


@app.route("/train", methods=["GET"])
def train():
    docs, ids = util.fetch_all()

    TrainThread(docs, ids).start()

    check_model()

    return flask.jsonify(
        result="ok"
    )
