import gensim

import util


class DocIterator(object):

    def __init__(self, doc_list, label_list):
        self.doc_list = doc_list
        self.label_list = label_list

    def __iter__(self):
        for idx, doc in enumerate(self.doc_list):
            yield gensim.models.doc2vec.TaggedDocument(doc, ["article-%d" % self.label_list[idx]])


# Train model
def train(model, docs, labels):
    tokenized = util.tokenize(docs)

    it = DocIterator(tokenized, labels)

    model.build_vocab(it)

    print("train with %d docs" % len(docs))

    for epoch in range(20):
        print("iteration: %d" % (epoch + 1))
        model.train(it, total_examples=model.corpus_count, epochs=model.epochs)

    util.save_model(model)
