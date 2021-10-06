## Hello-Querydsl

#### ~~next - 페이징쿼리 작성~~

### ```기본조회 쿼리```


1) fetch()
   - 리스트로 결과를 반환하는 방법이다. 만약에 데이터가 없으면 빈 리스트를 반환해준다.


- 작성자 명으로 찾기
  
  
    public List<Post> findByAuthor(String author){
            return queryFactory.selectFrom(post)
                    .where(post.author.eq(author))
                    .fetch();
        }
     
- Title 명으로 찾기  

   
     public List<Post> findByContaingTitle(String title){
             return queryFactory.selectFrom(post)
                     .where(contatingTitle(title))
                     .fetch();
         }
   
   
   
![타이틀로찾기](https://user-images.githubusercontent.com/68090443/132236560-6e4285ea-e6ff-4e87-92b6-b33f36478433.PNG)

         
         
- Content 명으로 찾기    

      
     public List<Post> findByCntatingContent(String content){
                  return queryFactory.selectFrom(post)
                          .where(contatingContent(content))
                          .fetch();
              }
   

![컨텐츠로찾기](https://user-images.githubusercontent.com/68090443/132236622-a1695fa3-023b-4adc-a8af-15764125e67d.PNG)
 
  
-----------------------        
     
        
## ```동적쿼리```

### 1. BooleanBuilder 사용

   - title만 들어올 경우 
      - where title = :title
   - content만 들어올 경우     
      - where content = :content
   - 2개가 다 들어올 경우 
      - where title = :title or content = :content
   
   - 파리미터가 어떻게 오는지에 따라 where의 조건이 변경된다.
   
   이것을 코드로 구현하면 아래와 같다. 
   

         public List<Post> findByTitleContainingOrContentContaining(String title, String content) {
                BooleanBuilder builder = new BooleanBuilder();
                if (!StringUtils.isEmpty(title)){
                    builder.and(post.title.contains(title));
                }
                if (!StringUtils.isEmpty(content)){
                    builder.or(post.content.contains(content));
                }
                return queryFactory.selectFrom(post)
                        .where(builder)
                        .fetch();
            }
            
            
### 2. BooleanExpression 사용
    

        public List<Post> findByContaingTitleOrContatingContent(String title, String content){
            return queryFactory.selectFrom(post)
                    .where(contatingTitle(title)
                            .or(contatingContent(content)))
                    .fetch();
        }
        
        private BooleanExpression contatingTitle(String title){
            if(title==null){
                return null;  
            }
            return post.title.contains(title);
        }
        
        private BooleanExpression contatingContent(String content){
            if(content == null){
                return null;
            }
            return post.content.contains(content);
        }
        
- BooleanExpression은 where에서 사용할 수 있는 값인데, 이 값은 ,를 and조건으로 사용한다.
- where에 null이 들어올 경우 조건문에서 제외됨(무시한다)


BooleanExpression을 사용하니 처음에 사용했던 BooleanBulider보다 명확하게 쿼리를 확인할 수 있다. 
   
- 결과(값이 들어올 경우)
   
   
 ![동적쿼리](https://user-images.githubusercontent.com/68090443/132236718-c6f43c57-8a1a-4182-9ccd-24fb77480da0.PNG)


- 결과(null일경우)


![동적쿼리(모두입력안할경우 전체)](https://user-images.githubusercontent.com/68090443/132236751-45246c19-f270-4132-9d60-16d3ee25c664.PNG)
   


하지만 BooleanExpression을 사용할 경우 메서드를 이용에 따른 코드량 증가할 것같다. 실무에서는 어떤식으로 관리하는지 궁금하다.

-----------------------
## 페이징 + 동적쿼리

###  `````- 기본페이징`````

    
        public Page<Post> findPagePost(Pageable pageable){
            QueryResults<Post> postQueryResults = queryFactory
                    .selectFrom(post)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();
            long total = postQueryResults.getTotal(); // 전체 포스트
            List<Post> posts = postQueryResults.getResults();
    
            return new PageImpl(posts, pageable,total);
        }



- 쿼리
   
   
![기본페이징](https://user-images.githubusercontent.com/68090443/132487514-87702245-0609-47b9-a1c8-96da35b583ad.png)






###  ```-페이징 동적쿼리 (제목, 내용으로 찾기)```


    public Page<Post> dynamicPagePostv2(String title, String content, Pageable pageable){
        QueryResults<Post> postQueryResults = queryFactory
                   .selectFrom(post)
                   .where(contatingTitle(title)
                           .or(contatingContent(content)))
                   .offset(pageable.getOffset())
                   .limit(pageable.getPageSize())
                   .fetchResults();
        List<Post> posts = postQueryResults.getResults();
        Long total = postQueryResults.getTotal();
        return new PageImpl(posts, pageable , total);
     }



- 쿼리
   
   
   
![동적쿼리페이징](https://user-images.githubusercontent.com/68090443/132487706-77343d35-16de-4298-a40d-ebf898845f32.png)
   



###  ```- 페이징 동적쿼리(작성자, 제목, 내용으로찾기)```


    public Page<Post> dynamicPagePostV3(String author, String title, String content, Pageable pageable){
        QueryResults<Post> postQueryResults = queryFactory
                .selectFrom(post)
                .where( eqAuthor(author)
                        .or(contatingTitle(title)
                                .or(contatingContent(content)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Post> posts = postQueryResults.getResults();
        long total = postQueryResults.getTotal();
        return new PageImpl(posts, pageable , total);
    }


  

- 쿼리
   
   

 ![동적쿼리 v2](https://user-images.githubusercontent.com/68090443/132487770-438cd295-4fd3-4081-83d6-5d7fc13e161a.png)



   
-----------------------

## ```조인```

### 1. inner join

post(one) -< reply(many)

    /**
     * 댓글조회
     * @param postId
     * @return
     */
    public List<Reply> findAllReplyByPostId(Long postId){
        return queryFactory.selectFrom(reply)
                .join(reply.post, post)
                .where(reply.post.id.eq(postId))
                .fetch();
    }
    
    on 절 생략가능 -> on(reply.post.id = post.id)
    
- 결과
   
   
   ![innerjoin](https://user-images.githubusercontent.com/68090443/132236962-2d150ac2-f136-4679-95a6-baf96dc0b7ed.PNG)

   
### 2. left join


     public List<Tuple> leftJoin(){
            return queryFactory.select(post,reply)
                    .from(post)
                    .leftJoin(post.replies, reply)
                    .fetch();
        }
    
    on 절 생략가능 on(post.id = reply.post.id)
   
-결과 
   
   
   ![leftjoin](https://user-images.githubusercontent.com/68090443/132236822-f7f9a579-591d-4da9-9523-53575d3e546c.PNG)



### 3. right join


     public List<Tuple> rightJoin(){
            return queryFactory.select(post,reply)
                    .from(post)
                    .rightJoin(post.replies, reply)
                    .fetch();
        }
    
     on 절 생략가능 on(post.id = reply.post.id)

-결과
   
   
   ![rightjoin](https://user-images.githubusercontent.com/68090443/132236854-7a0a42cb-2479-4442-8ab2-be53edd82300.PNG)


### 4. fetch join

 - 페치 조인은 jpa 사용시 가장 기본적으로 사용하는 성능 최적화 방식이다. 연관된 엔티티나 컬렉션을 한번에 같이 조회해온다.


    public List<Post> fetchJoin(){
            return queryFactory.selectFrom(post)
                    .join(post.replies, reply).fetchJoin()
                    .fetch();
        }

- 결과
   
   
   ![패치조인](https://user-images.githubusercontent.com/68090443/132236900-36d23916-c7ed-4cfb-8b43-fccc6e6472c8.PNG)



---------------------
### 삽질
- 테스트 코드를 작성하고 테스트를 진행하려니 

      ERROR  
      
      can`t ot find ssymbol 
      symbol: class QPost
      location: package com.bs.helloquerydsl.domain
 
구글링을 해보니 많은 사람들이 해당 문제가 발생한걸 볼수 있었다... 

Intellij에서 QueryDSL를 사용할 때 QClass가 실제 존재함에도 불구하고, 인식하지 못하는 문제이다.

즉 genereated 폴더가 패스에 등록되어 있지 않아 발생한 문제이다.

### ```해결방법(gradle 5 미만일 경우만 되는것같다....)```


- 1 

![querydsl오류해결1](https://user-images.githubusercontent.com/68090443/132129043-107b49f6-9564-45c1-905d-483cca78eb0b.png)


    Project Structure 를 클릭한다


- 2

![querydsl오류해결2](https://user-images.githubusercontent.com/68090443/132129126-16209139-876a-437a-8352-cf1788a27280.jpg)


    클릭후 해당 화면이 나오면 Modules를 선택하고 main을 눌러주면 해당 화면이 나온다.

- 3

![querydsl오류해결3](https://user-images.githubusercontent.com/68090443/132129148-19d56614-c93f-4598-b4fc-fcae1f1da6fc.png)


    해당화면에서 genereated폴더에 오른쪽 버튼을 누르고 Sources를 누르고 APPLY를 눌러주면 genereated폴더가 패스에 등록된다.
    
    이제 'generated'폴더를 Source로 인식해서 더 이상 오류를 보여주지 않습니다.
    
    잘 사용하면 끝이다!!

### ```이렇게 해도 QClass 관련 에러가 발생했다...``` 이유룰 찾아보니 gradle 버전이 6 이상이라 다른 방법으로 접근 해야했다. 

## ```해결방법(gradle 6이상)```
- QueryDsl 설정 부문

      
    buildscript {
        ext {
            queryDslVersion = "4.4.0" // QueryDsl버전
        }
    }
    
    plugins {
        id 'org.springframework.boot' version '2.5.4'
        id 'io.spring.dependency-management' version '1.0.11.RELEASE'
        id 'java'
    }
    
    dependencies {
        .....
        
        //QueryDsl 설정
        implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
        annotationProcessor(
                "javax.persistence:javax.persistence-api",
                "javax.annotation:javax.annotation-api",
                "com.querydsl:querydsl-apt:${queryDslVersion}:jpa")
    }
    
    sourceSets {
        main {
            java {
                srcDirs = ["$projectDir/src/main/java", "$projectDir/build/generated"]
            }
        }
    }      
        
        
이와 같은 방법으로 설정하니 오류없이 잘 돌아갔다.


---
   
### (TIL)[https://github.com/beomsun1234/TIL/tree/main/Spring-Data/Querydsl]
   
   
---
   
참고 사이트
- https://lteawoo.tistory.com/25
- https://javachoi.tistory.com/397
   

