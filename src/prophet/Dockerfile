from openjdk:8-alpine

VOLUME ./model:/usr/src/app/model
EXPOSE 8080

ENV MODEL_PATH /usr/src/app/model/article.model
ENV FLASK_APP src/app

WORKDIR /usr/src/app
COPY . .
RUN apk update \
    && apk upgrade \
    && apk add --no-cache bash \
    && apk add --no-cache --virtual=build-dependencies unzip \
    && apk add --no-cache curl
    && apk add --no-cache build-base
### Get Python, PIP
RUN apk add --no-cache python3 \
    && python3 -m ensurepip \
    && pip3 install --upgrade pip setuptools \
    && rm -r /usr/lib/python*/ensurepip && \
    if [ ! -e /usr/bin/pip ]; then ln -s pip3 /usr/bin/pip ; fi && \
    if [[ ! -e /usr/bin/python ]]; then ln -sf /usr/bin/python3 /usr/bin/python; fi && \
    rm -r /root/.cache
RUN pip install -r requirements.txt
CMD flask run --host 0.0.0.0 --port 8080