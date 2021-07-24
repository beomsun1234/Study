# 엔티티 설계시 주의점

<h3><b>엔티티에는 가급적 Setter를 사용하지 말자</b></h3><br>
- 변경 포인트가 너무 많아서, 유지보수가 어렵다. 나중에 리펙토링으로 Setter 제거




<h3>모든 연관관계는 지연로딩으로 설정!</h3>

- 즉시로딩( EAGER )은 예측이 어렵고, 어떤 SQL이 실행될지 추적하기 어렵다. 특히 JPQL을 실행할 때 N+1 
문제가 자주 발생한다.
- 실무에서 모든 연관관계는 지연로딩( LAZY )으로 설정해야 한다.

- 연관된 엔티티를 함께 DB에서 조회해야 하면, fetch join 또는 엔티티 그래프 기능을 사용한다.
- @XToOne(OneToOne, ManyToOne) 관계는 기본이 즉시로딩이므로 직접 지연로딩으로 설정해야 한
다.


<h3>컬렉션은 필드에서 초기화 하자.</h3>
- 컬렉션은 필드에서 바로 초기화 하는 것이 안전하다.
  - null 문제에서 안전하다
  
  
<h3> API를 만들때 엔티티 외부 반환 X</h3>



<h3>변경 감지와 병합(merge)</h3>

준영속 엔티티?

- 영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다. (여기서는 itemService.saveItem(book) 에서 수정을 시도하는 Book 객체다.<br>
- Book 객체는 이미 DB 에 한번 저장되어서 식별자가 존재한다. 이렇게 임의로 만들어낸 엔티티도 기존 식별자를 가지고 있으면 준
영속 엔티티로 볼 수 있다.)


준영속 엔티티를 수정하는 2가지 방법
- 변경 감지 기능 사용
- 병합( merge ) 사용

<b>변경 감지 기능 사용</b>

        @Transactional
        void update(Item itemParam) { //itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
         Item findItem = em.find(Item.class, itemParam.getId()); //같은 엔티티를 조회한
        다.
         findItem.setPrice(itemParam.getPrice()); //데이터를 수정한다.
        }
        
        영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정하는 방법이다.
        트랜잭션 안에서 엔티티를 다시 조회 , 변경할 값 선택 트랜잭션 커밋 시점에 변경 감지(Dirty Checking)
        이 동작해서 데이터베이스에 UPDATE SQL 실행
        
병합 사용

- 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능이다.


        @Transactional
        void update(Item itemParam) { //itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
         Item mergeItem = em.merge(item);
        }      
        
병합시 동작 방식을 간단히 정리
1. 준영속 엔티티의 식별자 값으로 영속 엔티티를 조회한다.
2. 영속 엔티티의 값을 준영속 엔티티의 값으로 모두 교체한다.(병합한다.)
3. 트랜잭션 커밋 시점에 변경 감지 기능이 동작해서 데이터베이스에 UPDATE SQL이 실행
        
> 주의: 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 사용하면 모든 속성이
변경된다. 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.)


API 변환시 해당 에러 발생 할경우 <br>

    java.lang.IllegalStateException: Cannot call sendError() after the response has been committed '
    <br>

 ManyToOne, OneToMany 양방향 관계에서 Member 엔티티를 조회할때
- 스프링에서 JSON변환을 담당하는 Jackson 라이브러리를 이용해 Entity 객체를 그대로 JSON 문자열으로 변환시키게 되는데 Member 객체의 team 필드가 Team 엔티티를 참조하고, Team 객체의 members 필드가 Member 엔티티를 참조 하고 이를 변환 시키는 과정에서 같은 데이터가 반복적으로 출력이 되는 무한 루프가 발생하는 순환 참조 문제 발생

해결방법

1.<b>@JsonManagedReference, @JsonBackReference 추가</b>

@JsonManagedReference
- 양방향 관계에서 정방향(자식->부모) 참조할 변수에 어노테이션을 추가하면 직렬화에 포함된다

@JsonBackReference
- 양방향 관계에서 역방향(부모->자식) 참조로 어노테이션을 추가하면 직렬화에서 제외된다.


Order

 @JsonManagedReference
 
     @JsonManagedReference // 순환참조 방지
     @ManyToOne(fetch = FetchType.LAZY) //lazy = 지연로딩 // ManyTo x -> 다 lazy로 바꾸기 기본타입(eager)
     @JoinColumn(name = "member_id") //fk키
     private Member member;
 
Member 

@JsonBackReference

    @JsonBackReference
    @OneToMany(mappedBy = "member") //연관관계 주인이아니에요~~  오더테이블에있는 member필드에 의해 맵핑
    private List<Order> orders = new ArrayList<>(); //읽기 전용 //변경 x
    
    
2.<b>DTO를사용하여 반환</b>

주 원인은 양방향 관계에서 entity 자체를 controller에서 그대로 return 하도록 설계한 것이 문제, entity가 변경될일이 있을 때 유연하게 대응하기 어렵기 때문에
DTO를 만들어 필요한 데이터만 옮겨담아 controller에서 return하면 순환참조 문제를 막을 수 있음


3.연관관계 재 설정
양방향 관계는 필수가 아닌 선택이다 비즈니스 로직에 따라 재설정 할 필요가 있다



#JPA


