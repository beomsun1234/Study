## 배포하기
도커및 쿠버네티스가 설치되어 있다고 가정한다.

    cd kubernetes
    #PVC 생성
    kubectl create -f hello-volume-claim.yaml
    
    # mariadb 파드생성
    kubectl create -f hello-mariadb.yaml
    
    # spring app 파드생성
    kubectl create -f hello-app.yaml
    
    #파드 확인
    kubectl get pod
    
![버전1](https://user-images.githubusercontent.com/68090443/147843784-ec23f60e-98d0-4bb1-b171-bb8b3436bb02.PNG)


## 디플로이먼트 롤링업데이트

    kubectl set image deployment <디플로이먼트 이름> <컨테이너 이름>=<새 이미지>
    kubectl set image deployment -f <디플로이먼트 파일> <컨테이너 이름>=<새 이미지>
    

controller를 수정하고 빌드 후 도커이미지를 만들어 도커 허브에 다시 올리고 나면 파드를 삭제하고 다시 실행하지 않고 업데이트가 가능하다.

수정하고 도커허브에 push했다고 가정하고 버전을 번경해보자

    kubectl set image deployment demo-app-service hello-k8s-app-service=beomsun22/k8s-app
    
![롤링업데이트](https://user-images.githubusercontent.com/68090443/147843877-e70aa552-ea1b-4064-9941-e73eb63353bd.PNG)

위와 같이 파드가 교체되는걸 볼 수 있다

![롤링업데이트완료](https://user-images.githubusercontent.com/68090443/147843908-86e5ef1f-1c3f-470b-92e6-758623bbd3e3.PNG)

위는 업데이트 완료후 파드이다. 처음 파드명이랑 달라진걸 확인 할 수 있다.

localhsot:8080에 접속하면 버전이 바뀐걸 볼 수 있다.

![완료](https://user-images.githubusercontent.com/68090443/147843911-ec88bbbe-141d-4024-a23a-88b885c645f9.PNG)

