from openjdk:8-alpine

VOLUME ./model:/usr/src/app/model
EXPOSE 8080

WORKDIR /usr/src/app
COPY . .
RUN apk update \
    && apk upgrade \
    && apk add --no-cache bash \
    && apk add --no-cache --virtual=build-dependencies unzip \
    && apk add --no-cache curl \
    && apk add --no-cache build-base \
    && apk add --no-cache libffi-dev \
    && apk add --no-cache openssl-dev \
    && apk add --no-cache lapack-dev \
    && apk add --no-cache gfortran \
    && apk add --no-cache g++
### Get Python, PIP
RUN apk add --no-cache python3-dev \
    && python3 -m ensurepip \
    && pip3 install --upgrade pip setuptools \
    && rm -r /usr/lib/python*/ensurepip && \
    if [ ! -e /usr/bin/pip ]; then ln -s pip3 /usr/bin/pip ; fi && \
    if [[ ! -e /usr/bin/python ]]; then ln -sf /usr/bin/python3 /usr/bin/python; fi && \
    rm -r /root/.cache
# RUN apt-get update -y && \
# apt-get install -y python3-pip python3-dev build-essential && \
# pip3 install --upgrade pip
RUN pip install -r requirements.txt
RUN /bin/bash <(curl -s https://raw.githubusercontent.com/konlpy/konlpy/master/scripts/mecab.sh)

ENV MODEL_PATH /usr/src/app/model/article.model
ENV FLASK_APP src/app
ENV LD_LIBRARY_PATH /usr/lib/jvm/java-1.8-openjdk/jre/lib/amd64/server
ENV PYTHONUNBUFFERED 1
CMD flask run --host 0.0.0.0 --port 8080