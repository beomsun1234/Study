# hello docker

## 실행
순서

프로젝트 빌드
    
    맥
    ./gradlew build 
    
    윈도우
    
    콘솔로 이동 -> 명령 프롬프트(cmd)로 이동
    
    ./gradlew -> gradlew.bat를 실행
    
    

도커 이미지 만들기

    docker build -t bs1124/hello-docker .
   
    
도커 이미지 실행

    docker run -d -p 8080:8080 -it bs1124/hello-docker .
    
---

자세히 알아보자
 
[Docker](https://github.com/beomsun1234/TIL/tree/main/Docker)    
[spring도커사용](https://github.com/beomsun1234/TIL/tree/main/Docker/spring%EB%8F%84%EC%BB%A4%EC%82%AC%EC%9A%A9)