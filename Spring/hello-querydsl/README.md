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





      
      
        
        
