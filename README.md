# Spring Study
@Controller : 스프링이 자동으로 스프링 빈으로 등록한다. (내부에 @Component 애노테이션이 있어서 컴포넌트
              스캔의 대상이 됨)
              스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다.
              두가지 일은 한다(Component scan ,RequestMappingHandlerMapping)

@RequestMapping : 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다. 애노테이션을
                  기반으로 동작하기 때문에, 메서드의 이름은 임의로 지으면 된다


ModelAndView : 모델과 뷰 정보를 담아서 반환하면 된다.

RequestMappingHandlerMapping 은 스프링 빈 중에서 @RequestMapping 또는 @Controller 가
클래스 레벨에 붙어 있는 경우에 매핑 정보로 인식한다.

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







                
