## 디플로이먼트 롤링업데이트

    kubectl set image deployment <디플로이먼트 이름> <컨테이너 이름>=<새 이미지>
    kubectl set image deployment -f <디플로이먼트 파일> <컨테이너 이름>=<새 이미지>