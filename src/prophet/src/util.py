import gensim
import multiprocessing
import pymysql
from konlpy.tag import Okt
from konlpy.tag import Mecab

import os

host = "172.18.0.12"
port = 3306
user = "root"
passwd = "testament@@#"
db = "sibylla"
charset = "utf8"


def fetch_all():
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        conn.execute("select id, content from article")
        data = conn.fetchall()
        docs = [c[1] for c in data]
        ids = [c[0] for c in data]
        return docs, ids


def fetch_top_100(exclude):
    query = "SELECT id, content FROM article WHERE id != %s ORDER BY written_date LIMIT 100;"
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        conn.execute(query, exclude)
        result = conn.fetchall()
        return result


def fetch(id):
    query = "SELECT id, content from article where id = %s order by written_date;"
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        conn.execute(query, id)
        result = conn.fetchone()
        return result


def fetch_with(comparison, others):
    query = "select article1_id, article2_id, similarity from similarity where article1_id = %s and article2_id = %s"
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        founds = []
        not_founds = []
        for o in others:
            args = (min(comparison, o), max(comparison, o))
            conn.execute(query, args)
            result = conn.fetchone()
            if result is None:
                not_founds.append(o)
            else:
                founds.append((o, result[2]))

    return founds, not_founds


def fetch_similarity(article1_id, article2_id):
    query = "SELECT similarity FROM similarity WHERE article1_id=%s AND article2_id=%s;"
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        conn.execute(query, [article1_id, article2_id])
        result = conn.fetchone()
        if result is None:
            return None
        return result[0]


def insert_similarity(article1_id, article2_id, similarity):
    if article1_id > article2_id:
        tmp = article1_id
        article1_id = article2_id
        article2_id = tmp
    query = "INSERT INTO similarity(article1_id, article2_id, similarity) VALUES(%s, %s, %s)"
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        conn.execute(query, [article1_id, article2_id, similarity])


def set_trained(ids):
    with pymysql.connect(host=host, port=port, user=user, password=passwd, db=db, charset=charset) as conn:
        query = "update article set used_in_train=true where id=%s"
        conn.executemany(query, ids)


def create_model():
    # doc2vec parameters
    # used only when model is created newly
    vector_size = 300
    window_size = 15
    sampling_threshold = 1e-15
    worker_count = multiprocessing.cpu_count()

    return gensim.models.Doc2Vec(
        vector_size=vector_size,
        window_size=window_size,
        worker_count=worker_count,
        sampling_threshold=sampling_threshold, )


def load_model():
    model_path = os.environ.get("MODEL_PATH")
    if model_path is None:
        return None

    if not os.path.isfile(model_path):
        return create_model()

    return gensim.models.doc2vec.Doc2Vec.load(model_path)


def save_model(model):
    model_path = os.environ.get("MODEL_PATH")
    model.save(model_path)


def tokenize(doc_list):
    mecab = Mecab()
    # okt = Okt()

    stopwords = ["이", "가", "의", "을", "은", "를", "이다", "는", "에", "에서", "로", "으로", "[서소문사진관]", "중앙일보", "중부일보",
                 "기자", ".", "·", ",", "'", "(", ")", "\"", "[", "]"]

    tokenized = []

    for d in doc_list:
        tokens = mecab.morphs(d)
        # tokens = okt.morphs(d)
        tokens = [w for w in tokens if w not in stopwords]
        tokenized.append(tokens)

    return tokenized
