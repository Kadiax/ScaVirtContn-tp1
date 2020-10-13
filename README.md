# Commandes Scalabilite Virtualisation Conteneurisation
## Docker
Moteur d'execution de conteneurs.

Docker est un logiciel libre permettant 
de lancer des applications dans des conteneurs logiciels.

## Conteneur
Logiciel permettant de charger d'autres ressources logicielles dans un environnement cloisonné 
avec des fonctionnalitées systèmes.

## Liens utiles
* https://docs.docker.com/get-started/
* https://putaindecode.io/articles/les-dockerfiles/
* https://spring.io/guides/gs/spring-boot-docker/

## Commandes utiles
* docker --version
* docker image ls
* docker ps --all
* docker rm --force bb
* docker network ls
* docker network inspect bridge

#### Les alias 
* alias dkps='docker ps -a'
* alias dki='docker images'
* alias dkst='docker stop $(docker ps -a -q)'
* alias dkrm='docker rm $(docker ps -a -q)'

## TP1
### 1) Exemple de Docker File pour une appli springboot:
```Dockerfile
FROM openjdk:13-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
```

#### Build une image docker
docker build --tag productapp:1.0 .

ou

docker build -t productapp:1.0 .

#### Run une image docker
docker run --publish 8000:8080 --detach --name prodapp productapp:1.0

ou

docker run -p 8000:8080 productapp:1.0

#### Stopper un conteneur
docker rm --force prodapp


### 2) Créer une base de données Redis
* Récupération de l'image officiel du midleware Redis

docker pull redis

* Ajouter un service Redis

docker run --name some-redis -d redis

ou

docker run --name some-redis -d redis redis-server --appendonly yes

### 3) Connecter les deux conteneurs

#### Avec un link
Tout d'abord les deux conteneurs doivent être stoppés et le properties de l'appli srping boot doit contenir :

* spring.redis.host=some-redis

* spring.redis.port=6379

Commandes dockers pour un link après un mvn install pour que le build réusisse:

1) docker run --name some-redis -d redis 

2) docker build -t productapp:1.0 .

3) docker run --link some-redis -p 8000:8080 productapp:1.0

#### Avec une adresse ip
Tout d'abord il faut rechercher l'host du conteneur some-redis :

* docker network ls
* docker network inspect bridge

Ensuite il faut remplacer dans l'application properties le nom du conteneur par l'adresse ip

* spring.redis.host=172.31.249.9

Commandes docker :

1) docker run --name some-redis -d redis 

2) docker build -t productapp:1.0 .

3) docker run --publish 8000:8080 --detach --name prodapp productapp:1.0


## TP2
#### Les labels :
Les labels permettent d'attacher des données à une image docker, ces donnéees permettrons de filtrer les images.

Quelques exemples d'application:
* Stocker des information sur le build, des tags git et des datesd de release
* Accréditer des images à plusieurs auteurs
* afficher des informations de licenses
* Organiser ses images docker sur des critères

Exemple de label, ils se construisent sous forme de clé valeur:

* LABEL version="1.0" maintainer="Nick Janetakis <nick.janetakis@gmail.com>"
* LABEL "com.example.vendor"="ACME Incorporated"
* LABEL com.example.label-with-value="foo"
* LABEL version="1.0"
* LABEL description="This text illustrates \
  that label-values can span multiple lines."
  
#### Docker compose
Docker compose est un orchestrateur de services, c'est est un outil
 qui permet de décrire (dans un fichier YAML) et gérer (en ligne de commande) plusieurs conteneurs comme un ensemble de services inter-connectés.

Exemple de fichier docker-compose.yml :

```yml
version: '3'
services:
  app:
    build: .
    ports:
      - "8000:8080"
    links:
      - "db:some-redis"
  db:
    image: "redis:alpine"
    hostname: some-redis
    ports:
      - "6379:6379"
```

Commandes utile:

* docker-compose build

* docker-compose up

* docker-compose up -d

* docker-compose rm

* docker-compose ps

##### Etude sur la consommation des ressources

Commandes docker pour executer des commandes dans un conteneur :

* docker exec -ti %container_id% /bin/bash
* docker exec -ti scavirtcontntp1_app_1 pwd 
* docker exec -ti scavirtcontntp1_app_1 ls 

netstat, pour « network statistics », est une ligne de commande affichant 
des informations sur les connexions réseau, les tables de routage et 
un certain nombre de statistiques dont ceux des interfaces, sans
 oublier les connexions masquées, les membres multicast, et enfin, 
 les messages netlink. (Source : Wikipedia)

* docker exec -ti scavirtcontntp1_app_1 netstat -a

Oserver la consommation des ressources:
* docker exec -ti scavirtcontntp1_app_1 top  (q pour sortir)

Commande permettant de visualiser les ressources en temps réelle utilisées par
les conteneurs:

docker stats


##### Gestion des ressources allouées
Exemple :
```yml
version: '3'
services:
  app:
    build: .
    ports:
      - "8000:8080"
    links:
      - "db:some-redis"
    resources:
        limits:
            cpus: '0.25'
            memory: 166.2MiB
        reservations:
            cpus: '0.20'
            memory: 166.1MiB
  db:
    image: "redis:alpine"
    hostname: some-redis
    ports:
      - "6379:6379"
```
