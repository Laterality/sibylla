from openjdk:8

VOLUME ./model:/usr/src/app/model
EXPOSE 8080

ENV MODEL_PATH /usr/src/app/model/article.model
ENV FLASK_APP src/app

WORKDIR /usr/src/app
COPY . .
RUN apt-get-repository ppa:jonathonf/python-3.6
RUN apt-get update -y
RUN apt-get install -y python3.6 libpython3.6
RUN pip install -r requirements.txt
CMD flask run --host 0.0.0.0 --port 8080