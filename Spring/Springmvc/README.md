# 스프링 MVC 시작
@Controller
 - 스프링이 자동으로 스프링 빈으로 등록한다. (내부에 @Component 애노테이션이 있어서 컴포넌트
스캔의 대상이 됨)
 - 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다. <br>
 
@RequestMapping
 - 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다. 애노테이션을 기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다.<br>

ModelAndView : 모델과 뷰 정보를 담아서 반환하면 된다.

            @Controller
            public class SpringMemberFormControllerV1 {
                @RequestMapping("/springmvc/v1/members/new-form")
                public ModelAndView process() {
                    return new ModelAndView("new-form");
                  }
            }
            
            
            memberRepository.save(member);
             ModelAndView mv = new ModelAndView("save-result");
             mv.addObject("member", member);
             return mv;
  - mv.addObject("member", member)
    - 스프링이 제공하는 ModelAndView 를 통해 Model 데이터를 추가할 때는 addObject() 를 사용하면
    된다. 
  
    - 이 데이터는 이후 뷰를 렌더링 할 때 사용된다.
    
 
#스프링 MVC - 컨트롤러 통합
/**
 * 클래스 단위 -> 메서드 단위
 * @RequestMapping 클래스 레벨과 메서드 레벨 조합
 */
 
        @Controller
        @RequestMapping("/springmvc/v2/members")
        public class SpringMemberControllerV2 { private MemberRepository memberRepository = MemberRepository.getInstance();
        
                 @RequestMapping("/new-form")
                 public ModelAndView newForm() {
                 
                 return new ModelAndView("new-form");
                 
                 }
                 
                 @RequestMapping("/save")
                 public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
                         
                         String username = request.getParameter("username");
                         int age = Integer.parseInt(request.getParameter("age"));
                         Member member = new Member(username, age);
                         memberRepository.save(member);
                         ModelAndView mav = new ModelAndView("save-result");
                         mav.addObject("member", member);
                         return mav;
                 }
                 
                 @RequestMapping
                 public ModelAndView members() {
                         
                         List<Member> members = memberRepository.findAll();
                         ModelAndView mav = new ModelAndView("members");
                         mav.addObject("members", members);
                         return mav;
                 }
        }


- @RequestMapping("/springmvc/v2/members/new-form")
- @RequestMapping("/springmvc/v2/members")
- @RequestMapping("/springmvc/v2/members/save")<br>
물론 이렇게 사용해도 되지만, 컨트롤러를 통합한 예제 코드를 보면 중복을 어떻게 제거했는지 확인할 수 있다.<br>
클래스 레벨에 다음과 같이 @RequestMapping 을 두면 메서드 레벨과 조합이 된다.
    
            @Controller
            @RequestMapping("/springmvc/v2/members")
            public class SpringMemberControllerV2 {}

# 스프링 MVC - 실용적인 방법 

        /**
         * v3
         * Model 도입
         * ViewName 직접 반환
         * @RequestParam 사용
         * @RequestMapping -> @GetMapping, @PostMapping
         */
        @Controller
        @RequestMapping("/springmvc/v3/members")
        public class SpringMemberControllerV3 {
        
             private MemberRepository memberRepository = MemberRepository.getInstance();
             
             @GetMapping("/new-form")
             public String newForm() {
             
                    return "new-form";
             }
             @PostMapping("/save")
             public String save( @RequestParam("username") String username,
                                 @RequestParam("age") int age,
                                 Model model){
                                 
                    Member member = new Member(username, age);
                    memberRepository.save(member);
                    model.addAttribute("member", member);
                    return "save-result"; 
             }
             
         @GetMapping
             public String members(Model model) {
             
                    List<Member> members = memberRepository.findAll();
                    model.addAttribute("members", members);
                    return "members";
             }
        }
        
        
- Model 파라미터
  - save() , members() 를 보면 Model을 파라미터로 받는 것을 확인할 수 있다. 스프링 MVC도 이런 편의기능을 제공한다.
  
- ViewName 직접 반환
  - 뷰의 논리 이름을 반환할 수 있다.
  
- @RequestParam 사용
  - 스프링은 HTTP 요청 파라미터를 @RequestParam 으로 받을 수 있다.
  - @RequestParam("username") 은 request.getParameter("username") 와 거의 같은 코드라 생각하면 된다.
- 물론 GET 쿼리 파라미터, POST Form 방식을 모두 지원한다.

- @RequestMapping -> @GetMapping, @PostMapping
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
<h3> @RestController</h3><br>
- @Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다.
@RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다. 
따라서 실행 결과로 ok 메세지를 받을 수 있다.<br>
- @RequestMapping("/hello-basic")
/hello-basic URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
대부분의 속성을 배열[] 로 제공하므로 다중 설정이 가능하다. {"/hello-basic", "/hello-go"}
- 다음 두가지 요청은 다른 URL이지만, 스프링은 다음 URL 요청들을 같은 요청으로 매핑, (둘다 허용 /hello-basic , /hello-basic/) <br>

<h3>@RequestMapping</h3>
- 가장 우선순위가 높은 핸들러 매핑과 핸들러 어댑터는 RequestMappingHandlerMapping , RequestMappingHandlerAdapter 이다. <br>
- @RequestMapping 의 앞글자를 따서 만든 이름인데, 이것이 바로 지금 스프링에서 주로 사용하는 애노테이션 기반의 컨트롤러를 지원하는 매핑과 어댑터이다. 실무에서는 99.9% 이 방식의 컨트롤러를
사용

<h3>HTTP 메서드(GET, HEAD, POST, PUT, PATCH, DELETE)<br></h3>
-> @RequestMapping 에 method 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드와 무관하게
호출된다.
모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE

<h2>PathVariable(경로변수)사용</h2>

-  변수명이 같으면 생략 가능 @PathVariable("userId") String userId -> @PathVariable userId <br>
-  <p>사용법<p/>
 
 
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
       
   - <p>사용법/</p>


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
                    

