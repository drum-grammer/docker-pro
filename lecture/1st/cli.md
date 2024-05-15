# cli example

## Part I : BASIC Image, Container CRUD

### 1. [Download an image from a registry](https://docs.docker.com/engine/reference/commandline/pull/)
- 사용법
```shell
 docker pull [OPTIONS] NAME[:TAG|@DIGEST]
```
- 예제
```shell
docker pull httpd
```

### 2. [List images](https://docs.docker.com/engine/reference/commandline/images/)
- 사용법
```shell
 docker images [OPTIONS] [REPOSITORY[:TAG]]
```
- 예제
```shell
 docker images
```

### 3. [Remove one or more images](https://docs.docker.com/engine/reference/commandline/rmi/)
- 사용법
```shell
 docker rmi [OPTIONS] IMAGE [IMAGE...]
```
- 예제
```shell
docker rmi 6026ab9b44cc
```

### 4. [Create and run a new container from an image](https://docs.docker.com/engine/reference/commandline/run/)
- 사용법
```shell
 docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```
- 예제
```shell
 docker run httpd
 docker run --name secondContainer httpd
 docker run -p 8888:80 -v /Users/jk/wanted/docker-pro/lecture/1st:/usr/local/apache2/htdocs httpd
```

### 5. [Start one or more stopped containers](https://docs.docker.com/engine/reference/commandline/start/)
- 사용법
```shell
 docker start [OPTIONS] CONTAINER [CONTAINER...]
```
- 예제
```shell
 docker start c8274d6a6273
```

### 6. [Stop one or more running containers](https://docs.docker.com/engine/reference/commandline/stop/)
- 사용법
```shell
 docker stop [OPTIONS] CONTAINER [CONTAINER...]
```
- 예제
```shell
 docker stop 9b0f49de746c
 docker stop -a
```

### 7. [Fetch the logs of a container](https://docs.docker.com/engine/reference/commandline/logs/)
- 사용법
```shell
 docker logs [OPTIONS] CONTAINER
```
- 예제
```shell
docker logs second
docker logs second -f
```

### 8. [Remove one or more containers](https://docs.docker.com/engine/reference/commandline/rm/)
- 사용법
```shell
 docker rm [OPTIONS] CONTAINER [CONTAINER...]
```
- 예제
```shell
docker rm 6026ab9b44cc
docker rm second -f
```

### 9. [Execute a command in a running container](https://docs.docker.com/engine/reference/commandline/exec/)
- 사용법
```shell
 docker exec [OPTIONS] CONTAINER COMMAND [ARG...]
```
- 예제
```shell
docker exec -it awesome_elion /bin/sh
```

### 10. [Manage containers](https://docs.docker.com/engine/reference/commandline/container/)
- 사용법
```shell
 docker container COMMAND
```

#### 10-1. [Remove all stopped containers](https://docs.docker.com/engine/reference/commandline/container_prune/)
- 사용법
```shell
 docker container prune [OPTIONS]
```
- 예제
```shell
 docker container prune
```

#### 10-2. [Display a live stream of container(s) resource usage statistics](https://docs.docker.com/engine/reference/commandline/container_stats/)
- 사용법
```shell
 docker container stats [OPTIONS] [CONTAINER...]
```
- 예제
```shell
 docker container stats
```


## Part II : Image Management

### 1. [Manage images](https://docs.docker.com/engine/reference/commandline/image/)
- 사용법
```shell
 docker image COMMAND
```

#### 1-1. [Remove unused images](https://docs.docker.com/engine/reference/commandline/image_prune/)
- 사용법
```shell
 docker image prune [OPTIONS]
```
- 예제
```shell
 docker image prune
```

#### 1-2. [Display detailed information on one or more images](https://docs.docker.com/engine/reference/commandline/image_inspect/)
- 사용법
```shell
 docker image inspect [OPTIONS]
```
- 예제
```shell
docker image inspect httpd
```

#### 1-3. [Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE](https://docs.docker.com/engine/reference/commandline/image_tag/)
- 사용법
```shell
 docker image tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
```
- 예제
```shell
docker image tag my-httpd drumgrammer/my-httpd:latest
```

### 2. [Upload an image to a registry](https://docs.docker.com/engine/reference/commandline/push/)
- 사용법
```shell
 docker push [OPTIONS] NAME[:TAG]
```
- 예제
```shell
docker push drumgrammer/my-httpd:latest
```


## Part III : Network Management

### 1. [Manage networks](https://docs.docker.com/reference/cli/docker/network/)
- 사용법
```shell
 docker network COMMAND
```

#### 1-1. [Create a network](https://docs.docker.com/reference/cli/docker/network/create/)
- 사용법
```shell
 docker network create [OPTIONS] NETWORK
```
- 예제
```shell
 docker network create my-network
```
#### 1-2. [Display detailed information on one or more networks](https://docs.docker.com/reference/cli/docker/network/inspect/)
- 사용법
```shell
 docker network inspect [OPTIONS] NETWORK [NETWORK...]
```
- 예제
```shell
 docker network inspect my-network
```
#### 1-3. [List networks](https://docs.docker.com/reference/cli/docker/network/ls/)
- 사용법
```shell
docker network ls [OPTIONS]
```
- 예제
```shell
 docker network ls
```
#### 1-4. [Remove all unused networks](https://docs.docker.com/reference/cli/docker/network/prune/)
- 사용법
```shell
 docker network prune [OPTIONS]
```
- 예제
```shell
 docker network prune
```

## Others

### 1. [Log in to a registry](https://docs.docker.com/engine/reference/commandline/login/)
- 사용법
```shell
 docker login [OPTIONS] [SERVER]
```
- 예제
```shell
 docker login
```

### 2. [Log out from a registry](https://docs.docker.com/engine/reference/commandline/logout/)
- 사용법
```shell
 docker logout [SERVER]
```
- 예제
```shell
 docker logout
```

### 3. [Manage Docker](https://docs.docker.com/engine/reference/commandline/system/)
- 사용법
```shell
 docker system COMMAND
```

#### 3-1. [Remove unused data](https://docs.docker.com/engine/reference/commandline/system_prune/)
- 사용법
```shell
 docker system prune [OPTIONS]
```
- 예제
```shell
 docker system prune
```


## Dockerfile 활용
1. Dockerfile 예제
```Dockerfile
FROM httpd:latest
COPY  index.html /usr/local/apache2/htdocs/index.html
EXPOSE 80
```
2. 이미지 만들기
```shell
docker build -t my-httpd .
```
3. 도커파일로 생성된 이미지로 컨테이너 실행하기
```shell
docker run -d -p 8888:80 my-httpd
```
