import gensim
import multiprocessing
import pymysql
from konlpy.tag import Okt
from konlpy.tag import Mecab

import os

host = "altair.latera.kr"
port = 3306
user = "sibylla_admin"
passwd = "testament@@#"
db = "sibylla"
charset = "utf8"


def fetch_untrained_data():
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        conn.execute("select id, content from article where used_in_train=false")
        data = conn.fetchall()
        docs = [c[1] for c in data]
        ids = [c[0] for c in data]
        return docs, ids


def fetch_top_100():
    query = "SELECT id FROM article ORDER BY written_date LIMIT 100;"
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        conn.execute(query)
        result = conn.fetchall()
        data = [c[0] for c in result]
        return data


def set_trained(ids):
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        query = "update article set used_in_train=true where id=%d"
        conn.executemany(query, ids)


def load_model():
    # doc2vec parameters
    # used only when model is created newly
    vector_size = 300
    window_size = 15
    sampling_threshold = 1e-15
    worker_count = multiprocessing.cpu_count()

    model_path = os.environ.get("MODEL_PATH")
    if model_path is None:
        return None

    if not os.path.isfile(model_path):
        return gensim.models.Doc2Vec(
            vector_size=vector_size,
            window_size=window_size,
            worker_count=worker_count,
            sampling_threshold=sampling_threshold,)

    return gensim.models.doc2vec.Doc2Vec.load(model_path)


def save_model(model):
    model_path = os.environ.get("MODEL_PATH")
    model.save(model_path)


def tokenize(doc_list):
    mecab = Mecab()
    # okt = Okt()

    stopwords = ["이", "가", "의", "을", "은", "를", "이다", "는", "에", "에서", "로", "으로", "중앙일보", "중부일보",
                 "기자", ".", "·", ",", "'", "(", ")", "\"", "[", "]"]

    tokenized = []

    for d in doc_list:
        tokens = mecab.morphs(d)
        # tokens = okt.morphs(d)
        tokens = [w for w in tokens if w not in stopwords]
        tokenized.append(tokens)

    return tokenized
