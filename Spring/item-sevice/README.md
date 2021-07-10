- 디자이너
  - 요구사항에 맞도록 디자인하고, 디자인 결과물을 웹 퍼블리셔에게 넘겨준다.
- 웹 퍼블리셔
  - 다자이너에서 받은 디자인을 기반으로 HTML, CSS를 만들어 개발자에게 제공한다.
- 백엔드 개발자
  - 디자이너, 웹 퍼블리셔를 통해서 HTML 화면이 나오기 전까지 시스템을 설계하고, 핵심 비즈니스 모델을 개발한다. 이후 HTML이 나오면 이 HTML을 뷰 템플릿으로 변환해서 동적으로 화면을 그리고, 또 웹 화면의 흐름을 제어한다.
  
  
 <b>컨트롤러 로직은 itemRepository에서 모든 상품을 조회한 다음에 모델에 담는다. 그리고 뷰 템플릿을
  호출한다.</b>
  

  
  <b>@RequiredArgsConstructor</b><br>
  - final 이 붙은 멤버변수만 사용해서 생성자를 자동으로 만들어준다. 
  
  
          public BasicItemController(ItemRepository itemRepository) {
           this.itemRepository = itemRepository;
          }
          
  이렇게 생성자가 딱 1개만 있으면 스프링이 해당 생성자에 @Autowired 로 의존관계를 주입해준다. 
  따라서 final 키워드를 빼면 안된다!, 그러면 ItemRepository 의존관계 주입이 안된다.<br>
  
  
 <b>@PostConstruct</b>
  - 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다
  - 여기서는 간단히 테스트용 테이터를 넣기 위해서 사용했다.
  
  
  #타임리프
  타임리프 사용 선언
    
    html xmlns:th="http://www.thymeleaf.org"
   
    th:href="@{/css/bootstrap.min.css}"
    href="value1" 을 th:href="value2" 의 값으로 변경한다.
    
 - 타임리프 뷰 템플릿을 거치게 되면 원래 값을 th:xxx 값으로 변경한다. 만약 값이 없다면 새로 생성한다.<br>
 - HTML을 그대로 볼 때는 href 속성이 사용되고, 뷰 템플릿을 거치면 th:href 의 값이 href 로 대체되면서 동적으로 변경할 수 있다.
   
   
<b>타임리프 핵심</b>
  
   - 핵심은 th:xxx 가 붙은 부분은 서버사이드에서 렌더링 되고, 기존 것을 대체한다. 
   - th:xxx 이 없으면 기존 html의 xxx 속성이 그대로 사용된다.
   - HTML을 파일로 직접 열었을 때, th:xxx 가 있어도 웹 브라우저는 ht: 속성을 알지 못하므로 무시한다.
   - 따라서 HTML을 파일 보기를 유지하면서 템플릿 기능도 할 수 있다.
   
<b>URL 링크 표현식 - @{...}, </b>

      th:href="@{/css/bootstrap.min.css}"
       @{...} : 타임리프는 URL 링크를 사용하는 경우 @{...} 를 사용한다. 이것을 URL 링크 표현식이라 한다.
       URL 링크 표현식을 사용하면 서블릿 컨텍스트를 자동으로 포함한다.
       
       
상품 등록 폼으로 이동
<b>속성 변경 - th:onclick</b>

        onclick="location.href='addForm.html'"
        th:onclick="|location.href='@{/basic/items/add}'|
        
        
<b>리터럴 대체 - |...| </b>

        |...| :이렇게 사용한다.
        타임리프에서 문자와 표현식 등은 분리되어 있기 때문에 더해서 사용해야 한다.
        <span th:text="'Welcome to our application, ' + ${user.name} + '!'">
        다음과 같이 리터럴 대체 문법을 사용하면, 더하기 없이 편리하게 사용할 수 있다.
        <span th:text="|Welcome to our application, ${user.name}!|">
        
        결과를 다음과 같이 만들어야 하는데
        location.href='/basic/items/add'
        그냥 사용하면 문자와 표현식을 각각 따로 더해서 사용해야 하므로 다음과 같이 복잡해진다.
        th:onclick="'location.href=' + '\'' + @{/basic/items/add} + '\''"
        리터럴 대체 문법을 사용하면 다음과 같이 편리하게 사용할 수 있다.
        th:onclick="|location.href='@{/basic/items/add}'|
        
<b>반복 출력 - th:each</b>

      <tr th:each="item : ${items}">
            
반복은 th:each 를 사용한다. 이렇게 하면 모델에 포함된 items 컬렉션 데이터가 item 변수에 하나씩
포함되고, 반복문 안에서 item 변수를 사용할 수 있다. <br> 

컬렉션의 수 만큼 tr>..</tr 이 하위 테그를 포함해서 생성된다.


<b>변수 표현식 - ${...}</b>

    <td th:text="${item.price}">10000</td>
    
    모델에 포함된 값이나, 타임리프 변수로 선언한 값을 조회할 수 있다.
    프로퍼티 접근법을 사용한다. ( item.getPrice() )
    
<b>내용 변경 - th:text</b>

    <td th:text="${item.price}">10000</td>
    
 내용의 값을 th:text 의 값으로 변경한다. 여기서는 10000을 ${item.price} 의 값으로 변경한다.
        

<b>URL 링크 표현식2 - @{...},</b>

    th:href="@{/basic/items/{itemId}(itemId=${item.id})}"
- 상품 ID를 선택하는 링크를 확인해보자.
  - URL 링크 표현식을 사용하면 경로를 템플릿처럼 편리하게 사용할 수 있다.
  - 경로 변수( {itemId} ) 뿐만 아니라 쿼리 파라미터도 생성한다.
    
            예) th:href="@{/basic/items/{itemId}(itemId=${item.id}, query='test')}"
            생성 링크: http://localhost:8080/basic/items/1?query=test
            

<b>URL 링크 간단히</b>

    th:href="@{|/basic/items/${item.id}|}"
    
상품 이름을 선택하는 링크를 확인해보자. 리터럴 대체 문법을 활용해서 간단히 사용할 수도 있다.<br>


<b>상품 상세</b><br>

상품 상세 컨트롤러와 뷰를 개발하자.<br>
BasicItemController에 추가    

        @GetMapping("/{itemId}")
        public String item(@PathVariable Long itemId, Model model) {
         Item item = itemRepository.findById(itemId);
         model.addAttribute("item", item);
         return "basic/item";
        }
        
     
<b>PathVariable 로 넘어온 상품ID로 상품을 조회하고, 모델에 담아둔다. 그리고 뷰 템플릿을 호출한다.</b>


<b>속성 변경 - th:value</b>

        th:value="${item.id}"
        
모델에 있는 item 정보를 획득하고 프로퍼티 접근법으로 출력한다. ( item.getId() )
value 속성을 th:value 속성으로 변경한다.


<h3>상품 등록 폼</h3>

BasicItemController에 추가

    @GetMapping("/add")
    public String addFrom() {
        return "basic/addForm";
    
    }
    
상품 등록 폼은 단순히 뷰 템플릿만 호출한다<br>


<b>속성 변경 - th:action</b>

    th:action
- HTML form에서 action 에 값이 없으면 현재 URL에 데이터를 전송한다.
- 상품 등록 폼의 URL과 실제 상품 등록을 처리하는 URL을 똑같이 맞추고 HTTP 메서드로 두 기능을 구분한다.
- 상품 등록 폼: GET /basic/items/add
- 상품 등록 처리: POST /basic/items/add
- 이렇게 하면 하나의 URL로 등록 폼과, 등록 처리를 깔끔하게 처리할 수 있다.
- 취소
- 취소시 상품 목록으로 이동한다.
     
       th:onclick="|location.href='@{/basic/items}'|"
       
       

<h3>상품 등록 처리 - @ModelAttribute</h3>

상품 등록 폼에서 전달된 데이터로 실제 상품을 등록 처리해보자.<br>

상품 등록 폼은 다음 방식으로 서버에 데이터를 전달한다.<br>

- POST - HTML Formcontent-type: application/x-www-form-urlencoded

- 메시지 바디에 쿼리 파리미터 형식으로 전달 itemName=itemA&price=10000&quantity=10

- 예) 회원 가입, 상품 주문, HTML Form 사용


@RequestParam 으로 변수를 하나하나 받아서 Item 을 생성하는 과정은 불편했다.<br>
이번에는 @ModelAttribute 를 사용해서 한번에 처리

        @PostMapping("/add")
        public String addItemV2(@ModelAttribute("item") Item item, Model model) {
         itemRepository.save(item);
         //model.addAttribute("item", item); //자동 추가, 생략 가능
         return "basic/item";
        }
        
- @ModelAttribute - 요청 파라미터 처리
- @ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.
- @ModelAttribute - Model 추가
- @ModelAttribute 는 중요한 한가지 기능이 더 있는데, 바로 모델(Model)에 @ModelAttribute 로
지정한 객체를 자동으로 넣어준다. 지금 코드를 보면 model.addAttribute("item", item) 가 주석처리
되어 있어도 잘 동작하는 것을 확인할 수 있다.
- 모델에 데이터를 담을 때는 이름이 필요하다. 이름은 @ModelAttribute 에 지정한 name(value) 속성을
사용한다. 
- 만약 다음과 같이 @ModelAttribute 의 이름을 다르게 지정하면 다른 이름으로 모델에 포함된다.
        
          @ModelAttribute("hello") Item item 이름을 hello 로 지정
   
          model.addAttribute("hello", item); 모델에 hello 이름으로 저장
          
@ModelAttribute 의 이름을 생략할 수 있다.
         
          @PostMapping("/add")
          public String addItemV3(@ModelAttribute Item item) {
           itemRepository.save(item);
           return "basic/item";
          }
          
@ModelAttribute 의 이름을 생략하면 모델에 저장될 때 클래스명을 사용한다. 이때 클래스의 첫글자만
소문자로 변경해서 등록한다. <br>

예) @ModelAttribute 클래스명 모델에 자동 추가되는 이름
  -  Item item
  -  HelloWorld helloWorld
  
  
  
상품 수정은 상품 등록과 전체 프로세스가 유사하다.<br>
  
- GET /items/{itemId}/edit : 상품 수정 폼
- POST /items/{itemId}/edit : 상품 수정 처리


<h3> 리다이렉트</h3>
상품 수정은 마지막에 뷰 템플릿을 호출하는 대신에 상품 상세 화면으로 이동하도록 리다이렉트를
호출한다.

 - 스프링은 redirect:/... 으로 편리하게 리다이렉트를 지원한다.
 
             redirect:/basic/items/{itemId}"
             
컨트롤러에 매핑된 @PathVariable 의 값은 redirect 에도 사용 할 수 있다.

    redirect:/basic/items/{itemId}  {itemId} 는 @PathVariable Long itemId 의 값을
        그대로 사용한다.
        
        
<h3>RedirectAttributes</h3>

상품을 저장하고 상품 상세 화면으로 리다이렉트 한 것 까지는 좋았다. 그런데 고객 입장에서 저장이 잘 된
것인지 안 된 것인지 확신이 들지 않는다. 그래서 저장이 잘 되었으면 상품 상세 화면에
"저장되었습니다"라는 메시지를 보여달라는 요구사항이 왔다.


        @PostMapping("/add")
        public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
         Item savedItem = itemRepository.save(item);
         redirectAttributes.addAttribute("itemId", savedItem.getId());
         redirectAttributes.addAttribute("status", true);
         return "redirect:/basic/items/{itemId}";
        }
        
        실행해보면 다음과 같은 리다이렉트 결과가 나온다.
        http://localhost:8080/basic/items/3?status=true
        
        
- RedirectAttributes
  - RedirectAttributes 를 사용하면 URL 인코딩도 해주고 pathVarible , 쿼리 파라미터까지 처리해준다.redirect:/basic/items/{itemId}
  - pathVariable 바인딩: {itemId} , 나머지는 쿼리 파라미터로 처리: ?status=true<br>
  
  
  

  - th:if : 해당 조건이 참이면 실행
  - ${param.status} : 타임리프에서 쿼리 파라미터를 편리하게 조회하는 기능
   
        <h2 th:if="${param.status}" th:text="'저장 완료!'"></h2>

      
  원래는 컨트롤러에서 모델에 직접 담고 값을 꺼내야 한다. 그런데 쿼리 파라미터는 자주 사용해서
  타임리프에서 직접 지원한다. 뷰 템플릿에 메시지를 추가하고 실행해보면 "저장 완료!" 라는 메시지가 나오는 것을 확인할 수 있다
  
            