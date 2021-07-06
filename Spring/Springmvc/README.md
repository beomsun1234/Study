# 로깅 (logger)
<h2>로그 선언</h2>
<p>private Logger log = LoggerFactory.getLogger(getClass());</p>
<p>private static final Logger log = LoggerFactory.getLogger(Xxx.class)</p>
<p>@Slf4j : 롬복 사용 가능</p>

<h2>로그 선언</h2>
<p>log.info("hello")</p>
<p></p>System.out.println("hello") 시스템 콘솔로 직접 출력하는 것 보다 로그를 사용하면 다음과 같은 장점이 있다. 실무에서는 항상 로그를 사용해야 한다.</p>

<h2>로그 레벨 설정</h2>
LEVEL: TRACE > DEBUG > INFO > WARN > ERRO<br>
<p>개발서버 -> debug, 로컬pc -> trace , 운영서버 -> info

<h2>올바른 로그 사용법</h2>
<u>log.debug("data="+data)</u>
로그 출력 레벨을 info로 설정해도 해당 코드에 있는 "data="+data-> (data=data)가 실제 실행이 되어 버린다. 결과적으로 <u>문자 더하기 연산이 발생한다.</u><br>
<u>log.debug("data={}", data)</u> 로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다. 따라서 앞과 같은 의미없는 연산이발생하지 않는다.

<h2>매핑 정보</h2>
<u>@RestController</u><br>
<u>@Controller</u> 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다. <u>@RestController</u> 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 따라서 실행 결과로 ok 메세지를 받을 수 있다.

<h3>로그 사용시 장점</h3>
- 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정할 수 있다.<br>
- 로그 레벨에 따라 개발 서버에서는 모든 로그를 출력하고, 운영서버에서는 출력하지 않는 등 로그를 상황에
맞게 조절할 수 있다.<br>
- 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다.<br>
- 특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.<br>
- 성능도 일반 System.out보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등) 그래서 실무에서는 꼭 로그를
사용해야 한다.<br>

#요청매핑
@RestController<br>
- @Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
@RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 
따라서 실행 결과로 ok 메세지를 받을 수 있다.<br>
- @RequestMapping("/hello-basic")
/hello-basic URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
대부분의 속성을 배열[] 로 제공하므로 다중 설정이 가능하다. {"/hello-basic", "/hello-go"}
- 다음 두가지 요청은 다른 URL이지만, 스프링은 다음 URL 요청들을 같은 요청으로 매핑, (둘다 허용 /hello-basic , /hello-basic/) <br>

- HTTP 메서드(GET, HEAD, POST, PUT, PATCH, DELETE)<br>
-> @RequestMapping 에 method 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드와 무관하게
호출된다.
모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE

<h2>PathVariable(경로변수)사용</h2>

-  변수명이 같으면 생략 가능 @PathVariable("userId") String userId -> @PathVariable userId <br>
 - 사용법 
 
 
       @GetMapping("/mapping/{userId}/orders/{oreders}") //다중
         public String mappingPath(@PathVariable String userId,
                                   @PathVariable Long oreders){
             log.info("mappingPath usrrId={}",userId);
             return userId;
         }
 
 
 - 최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다.<br>
 /mapping/userA <br>
 /users/1 <br>
 
* Content-Type 헤더 기반 추가 매핑 Media Type<br>
  - consumes="application/json"
   * consumes="!application/json"
   * consumes="application/*"
   * consumes="*\/*"
   * MediaType.APPLICATION_JSON_VALUE
   * consumes = 소비하는 입장 요청에 컨텐츠타입을 소비하므로 consumes라 불린다
   - 예시<br>
         - consumes = "text/plain" <br>
         - consumes = {"text/plain", "application/*"}<br>
         - consumes = MediaType.TEXT_PLAIN_VALUE<br>
       
       
         @PostMapping(value = "/mapping-consume", consumes = "application/json")
            public String mappingConsumes() {
                 log.info("mappingConsumes");
                 return "ok";
             }
 

 - Accept 헤더 기반 Media Type
   * produces = "text/html"
   * produces = "!text/html"
   * produces = "text/*"
   * produces = "*\/*"
   - produces = "text/plain"
   - produces = {"text/plain", "application/*"}
   - produces = MediaType.TEXT_PLAIN_VALUE
   - produces = "text/plain;charset=UTF-8

 
       @PostMapping(value = "/mapping-produce", produces = "text/html")
            public String mappingProduces() {
            log.info("mappingProduces");
             return "ok";}
             
- @RequestMapping("/mapping/users") 클래스 레벨에 매핑 정보를 두면 메서드 레벨에서 해당 정보를 조합해서 사용한다.
  -  회원 목록 조회: GET /mapping/users
  - 회원 등록: POST /mapping/users
  - 회원 조회: GET /mapping/users/id1
  - 회원 수정: PATCH /mapping/users/id1
  - 회원 삭제: DELETE /mapping/users/id1



<h3>HTTP 요청이 보내는 데이터들을 스프링 MVC로 어떻게 조회?</h3>

<p>HTTP 요청 - 기본, 헤더 조회</p>

        @RequestMapping("/headers")
            public String headers(HttpServletRequest request,
                                  HttpServletResponse response,
                                  HttpMethod httpMethod,
                                  Locale locale,
                                  @RequestHeader MultiValueMap<String, String>
                                  headerMap,
                                  @RequestHeader("host") String host,
                                  @CookieValue(value = "myCookie", required = false)
                                  String cookie)
                                  
                                  
 - HttpMethod : HTTP 메서드를 조회한다. org.springframework.http.HttpMethod
 - Locale : Locale 정보를 조회한다.
 - @RequestHeader MultiValueMap<String, String> headerMap 
   - 모든 HTTP 헤더를 MultiValueMap 형식으로 조회한다.
 - @RequestHeader("host") String host
   - 특정 HTTP 헤더를 조회한다.
   - 속성
   - 필수 값 여부: required
   - 기본 값 속성: defaultValue
- @CookieValue(value = "myCookie", required = false) String cookie
   - 특정 쿠키를 조회한다.
   - 속성
      - 필수 값 여부: required
      - 기본 값: defaultValue
      
      
 <p>MultiValueMap</p>
 MAP과 유사한데, 하나의 키에 여러 값을 받을 수 있다.
 HTTP header, HTTP 쿼리 파라미터와 같이 하나의 키에 여러 값을 받을 때 사용한다.<br>
 keyA=value1&keyA=value2
 
         MultiValueMap<String, String> map = new LinkedMultiValueMap();
         map.add("keyA", "value1");
         map.add("keyA", "value2");
         //[value1,value2]
         List<String> values = map.get("keyA");
                    

<p>HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form</p>
클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 다음 3가지 방법을 사용한다.<br>

-  GET - 쿼리 파라미터/url?username=hello&age=20
    - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달
    - 예) 검색, 필터, 페이징등에서 많이 사용하는 방식
-  POST - HTML Form
    - content-type: application/x-www-form-urlencoded
    - 메시지 바디에 쿼리 파리미터 형식으로 전달 username=hello&age=20
    - 예) 회원 가입, 상품 주문, HTML Form 사용

-  HTTP message body에 데이터를 직접 담아서 요청
   - HTTP API에서 주로 사용, JSON, XML, TEXT
   - 데이터 형식은 주로 JSON 사용
   - POST, PUT, PATCH