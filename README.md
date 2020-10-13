# Commandes Scalabilite Virtualisation Conteneurisation

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

FROM openjdk:13-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]


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
