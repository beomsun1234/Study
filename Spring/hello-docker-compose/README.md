# hello docker compose

## 실행

프로젝트 빌드
    
    맥
    ./gradlew build
    
    윈도우
    gradlew build

Dockerfile 작성

    FROM adoptopenjdk/openjdk11:alpine-slim
    ARG JAR_FILE=build/libs/*.jar
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    EXPOSE 8080

docker-compose.yml 작성

    version: "3.7"
    services:
      database:
        container_name: spring-db
        image: mariadb
        ports:
        - 3306:3306
        volumes:
          - C:\Users\박범선\datadir:/var/lib/mysql
        environment:
          - MYSQL_DATABASE=mydb
          - MYSQL_USER=root
          - MYSQL_ROOT_PASSWORD=1234
        command: ['--character-set-server=utf8mb4', '--collation-server=utf8mb4_unicode_ci']
        restart: always
      application:
        ports:
        - 8080:8080
        container_name: spring-app
        build:
          context: .
          dockerfile: Dockerfile
        depends_on:
          - database
        restart: always
    
실행

    docker-compose up -d
    
---

자세히 알아보자

### [Docker-Compose](https://github.com/beomsun1234/TIL/tree/main/Docker)
### [MariaDB사용](https://github.com/beomsun1234/TIL/tree/main/Docker/MariadbDB%EC%82%AC%EC%9A%A9)