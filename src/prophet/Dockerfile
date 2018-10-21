from python:3

VOLUME ./model:/usr/src/app/model
EXPOSE 8080

ENV MODEL_PATH /usr/src/app/model/article.model

WORKDIR /usr/src/app
COPY . .
RUN pip install -r requirements.txt
CMD flask run --host 0.0.0.0 --port 8080