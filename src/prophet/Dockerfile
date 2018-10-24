from python:3

VOLUME ./model:/usr/src/app/model
EXPOSE 8080

ENV MODEL_PATH /usr/src/app/model/article.model
ENV FLASK_APP src/app

WORKDIR /usr/src/app
COPY . .
RUN pip install -r requirements.txt
RUN apt-get update -y
RUN apt-get install -y g++ openjdk-8-jdk
CMD flask run --host 0.0.0.0 --port 8080