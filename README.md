# KOI Wiki

## Docker

To build docker image (a *.jar file must be present in `api/app/target`) :

```bash
cd app/api

docker build -t <repo>/<image>[:<tag>] .
docker build -t knifeonlyi/koi-wiki-api:1.0.0
```

To push image in remote repository

```bash
docker push <repo>/<image>[:<tag>]
docker push knifeonlyi/koi-wiki-api:1.0.0
```

An example of docker-compose

```yaml
version: "3.6"

services:
  koi-wiki-api:
    container_name: koi-wiki-api
    image: knifeonlyi/koi-wiki-api
    ports:
      - "8080:8080"
    environment:
      KOI_WIKI_PROFILE: prod
      KOI_WIKI_DATABASE_HOST: koi-wiki-database
      KOI_WIKI_DATABASE_PORT: 5432
      KOI_WIKI_DATABASE_NAME: koi_wiki
      KOI_WIKI_DATABASE_USER: koi_wiki
      KOI_WIKI_DATABASE_PASSWORD: koi_wiki
    depends_on:
      - koi-wiki-database
    networks:
      - koi-wiki-prod-network

  koi-wiki-database:
    container_name: koi-wiki-database
    image: postgres:15.1-alpine3.17
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: koi_wiki
      POSTGRES_USER: koi_wiki
      POSTGRES_PASSWORD: koi_wiki
    networks:
      - koi-wiki-prod-network

networks:
  koi-wiki-prod-network:
    name: koi-wiki-prod-network
```