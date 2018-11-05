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

    check_model()

    article_1 = flask.request.args.get("article1")
    article_2 = flask.request.args.get("article2")

    article1 = util.fetch(article_1)
    article2 = util.fetch(article_2)
    tokens = util.tokenize([article1[1], article2[1]])
    similarity = str(model.docvecs.similarity_unseen_docs(model, tokens[0], tokens[1]))

    return flask.jsonify(
        result="ok",
        similarity=str(similarity)
    )


@app.route("/get-similarities", methods=["GET"])
def get_similarities():
    global model

    check_model()

    comparison = int(flask.request.args.get("article"))
    ctoken = util.tokenize([util.fetch(comparison)[1]])

    recent_articles = util.fetch_top_100(comparison)
    recent_article_ids = [a[0] for a in recent_articles]

    founds, not_founds = util.fetch_with(comparison, recent_article_ids)
    result_ids = [f[0] for f in founds]

    similarities = [f[1] for f in founds]

    not_found_articles = [a for a in recent_articles if a[0] in not_founds]

    for a in not_found_articles:
        sim = model.docvecs.similarity_unseen_docs(model, ctoken[0], util.tokenize([a[1]])[0])
        util.insert_similarity(comparison, a[0], sim.item())
        result_ids.append(a[0])
        similarities.append(str(sim))

    return flask.jsonify(
        result="ok",
        articleIds=result_ids,
        similarities=similarities
    )


@app.route("/train", methods=["GET"])
def train():
    docs, ids = util.fetch_all()

    TrainThread(docs, ids).start()

    return flask.jsonify(
        result="ok"
    )
