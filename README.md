# Project sibylla

Graduation work in semester 2, 2018.

Web service that collect News on web and recommend to user based they read.

## Before run

Set API server's address

`src/microservice/sibylla-view/src/lib/Api.ts`

```typescript
    // ...
    private static readonly BASE_URL = "http://sibylla.latera.kr/api"; // It must be your API server address
    // ...
```


For launch elasticsearch container,
```
$ grep vm.max_map_count /etc/sysctl.conf
vm.max_map_count=262144
```

To apply the setting on a live system type: `sysctl -w vm.max_map_count=262144`

Please see [link](https://www.elastic.co/guide/en/elasticsearch/reference/6.4/docker.html) for detail.

Also metricbeat container needs permission to access host docker
```
$ setfacl -m u:1000:rw /var/run/docker.sock
```

## Build docker image

Entire projects run in docker container. So first they need to be built.

It may take some time.

```
git clone https://github.com/Laterality/sibylla.git
cd sibylla
docker-compose build
```

## Run project

```
docker-compose up
```

Then, you must see the web page in `http://localhost`

```
curl http://localhost
```