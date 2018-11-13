FROM nginx

COPY ./conf.d /etc/nginx/conf.d
WORKDIR /usr/src/app
COPY . .
RUN apt-get update -yq \
    && apt-get install curl gnupg -yq \
    && curl -sL https://deb.nodesource.com/setup_8.x | bash \
    && apt-get install nodejs -yq
RUN npm install -g webpack-cli
CMD npm install && \
    node node_modules/webpack/bin/webpack.js