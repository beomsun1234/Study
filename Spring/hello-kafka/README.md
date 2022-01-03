## hello-kafka with kubernetes




### step1

application-db.yml, application-kafka.yml 작성 해준다.

application-db.yml

    spring:
      datasource:
        username: root
        password: 1234
        driver-class-name: org.mariadb.jdbc.Driver
        url: jdbc:mariadb://[ip]:30001/mydb


application-kafka.yml

    kafka:
      host: [ip]:9092


프로젝트 빌드 후 도커이미지를 만들고 도커 허브에 올린다. (과정 생략)


mariadb를 저장 할 pvc 생성(현재 볼륨을 나는 동적 프로비저닝 사용함고 있으므로 볼륨 생성 x)

    kubectl create -f pvc.yaml


mariadb 배포

    kubectl create -f k8s-mariadb.yaml
    

zookeeper 배포

    kubectl create -f k8s-zookeeper.yaml
    
kafka 배포

    파드 생성 전에 k8s-kafka.yaml 수정해야한다. 
    
    env:
        ....
        - name: KAFKA_ADVERTISED_LISTENERS
          value: PLAINTEXT://[ip]:9092   # [ip]부분에 자신의 로컬 ip 입력
          
    수정 후 배포

    kubectl create -f k8s-kafka.yaml
    

kafka 인스턴스 접근 후 토픽생성


    kubectl exec -it [카프카 파드명] bash
    
    kafka-topics.sh --create --bootstrap-server [서비스 명 or host ip ]:9092 --replication-factor 1 --partitions 1 --topic park
    

app 배포

    kubectl create -f k8s-app
    


## 결과

GET localhost:8080/message?message="hello"

[사진]