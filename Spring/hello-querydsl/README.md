![querydsl오류해결1](https://user-images.githubusercontent.com/68090443/132129035-d9f30a2d-c958-4b16-b062-81bb8065eb82.png)
## Hello-Querydsl

삽질
- 테스트 코드를 작성하고 테스트를 진행하려니 

      ERROR  
      
      can`t ot find ssymbol 
      symbol: class QPost
      location: package com.bs.helloquerydsl.domain
 
구글링을 해보니 많은 사람들이 해당 문제가 발생한걸 볼수 있었다... 

Intellij에서 QueryDSL를 사용할 때 QClass가 실제 존재함에도 불구하고, 인식하지 못하는 문제이다.

즉 genereated 폴더가 패스에 등록되어 있지 않아 발생한 문제이다.

### ```해결방법```


1.
![querydsl오류해결1](https://user-images.githubusercontent.com/68090443/132129043-107b49f6-9564-45c1-905d-483cca78eb0b.png)
Project Structure 를 클릭한다


2.
![querydsl오류해결2](https://user-images.githubusercontent.com/68090443/132129126-16209139-876a-437a-8352-cf1788a27280.jpg)
클릭후 해당 화면이 나오면 Modules를 선택하고 main을 눌러주면 해당 화면이 나온다.

3.
![querydsl오류해결3](https://user-images.githubusercontent.com/68090443/132129148-19d56614-c93f-4598-b4fc-fcae1f1da6fc.png)
해당화면에서 genereated폴더에 오른쪽 버튼을 누르고 Sources를 누르고 APPLY를 눌러주면 genereated폴더가 패스에 등록된다.

이제 'generated'폴더를 Source로 인식해서 더 이상 오류를 보여주지 않습니다.

잘 사용하면 끝이다!!




      
      
        
        
