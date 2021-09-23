## ```Hello File Upload (feat. Querydsl, Swagger)```

- 프로젝트 외부 경로로 파일(이미지)업로드 

- /images/** 를 통해 해당 이미지 불러옴

- board 조회시 n+1 발생
    - board와 file 관계에서 board는 연관관계 주인이 아니기에 외래키를 관리하지 않기 때문에 file필드는 존재하지 않는다. 
      해당 이슈는 JPA의 구현체인 Hibernate에서 프록시 기능의 한계로 지연로딩을 지원하지 못하기 때문에 발생한다. 좀 더 자세하게는 프록시 객체를 만들기 위해서는
      객체에 값이 있는지 없는지 알아야 한다. 그런데 board에서 file의 값을 알기 위해서는 모조건 file을 조회해야지만 알 수 있다.
      따라서 어차피 file에 대한 쿼리가 발생하기 때문에 hibernate는 프록시 객체를 만들 필요가 없어져 버린다... 결과적으로 지연로딩으로 설정하여도 동적하지 않는다.
      반대로 filed을 조회할때 board에 대한 정보를 가지고 있기에 프록시 객체를 생성 후 지연로딩으로 처리하게된다.
      해결방법은  ```OneToOne 단방향 연관관계로 수정하는 방법과```, ```fetch join과 entity graph를 사용하는방법``` 이 있다. 
      
    - 나는 Fetch Join을 사용하여 연관된 엔티티도 함께 조회(즉시로딩)하는 방법으로 해결했다(항상 board를 조회할 경우 file에 대한 정보도 알아야하기에 단방향으로 자를수는 없었다.)

```feat JPA에서의 1:1 연관관계 제약사항```

    JPA에서는 OneToOne(이하 1:1)관계에서 무조건적인 LazyLoading(지연로딩)을 지원하지 않는다.
    정확하게 말하면 특정조건을 모두 만족해야지만 LazyLoading이 작동한다. 
    그 외에는 객체를 참조하기 위한 패치전략을 Lazy로 지정하더라도 Eager(객체간 Join발동 또는 동시참조)로 작동한다.
        
    JPA는 객체의 참조가 프록시 기반으로 동작하는데, 연관관계가 있는 객체는 참조를 할때 기본적으로 null이 아닌 프록시 객체를 반환한다. 
    1:1관계에서는 null이 허용되는 경우, 프록시형태로 null객체를 반환할 수가 없기 때문이다. 
    1:N관계는 이미 배열의 형태로 이미 참조할 프록시 객체를 싸고 있기 때문에 그 객체가 null이라도 참조할때는 문제가 되지 않는다.
    따라서 JPA 구현체는 기본적으로 1:1관계에서는 지연로딩 를 허용하지 않고, 즉시 값을 읽어 들인다. 
    물론 Lazy를 설정할 수 있지만 특정조건을 모두 만족하지 않는다면 동작하지 않는다.

 출저 https://yongkyu-jang.medium.com/jpa-%EB%8F%84%EC%9E%85-onetoone-%EA%B4%80%EA%B3%84%EC%97%90%EC%84%9C%EC%9D%98-lazyloading-%EC%9D%B4%EC%8A%88-1-6d19edf5f4d3

<br>


### 엔티티(Member, Board, File)



![fileupload2](https://user-images.githubusercontent.com/68090443/134145183-03d1c361-ac2b-4669-9653-fa6602f2ec1b.PNG)


### Swagger



![swagger2](https://user-images.githubusercontent.com/68090443/134337240-284b8e16-14df-4a7c-9079-2b91e84209e1.PNG)



- board save



![boardsave](https://user-images.githubusercontent.com/68090443/134338231-568a36b6-35fe-400f-b594-4533a4baa01b.PNG)




- find all



![findall](https://user-images.githubusercontent.com/68090443/134338217-578b83fd-eda9-458d-b620-05a4f5c91ee1.PNG)




