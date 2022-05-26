## 멀티 모듈 프로젝트 만들기

### step 1. 루트 프로젝트 생성

인텔리제이 왼쪽 상단의 File -> New -> Project... -> Gradle -> Java 선택하여 프로젝트를 생성한다.
    
루트 프로젝트는 하위 모듈을 관리하는 역할이므로 src폴더를 지워줘도 된다.
    
### step 2. 모듈 생성
  
core, service, batch 3가지 모듈을 만들어 보자
    
프로젝트에서 오른쪽 클릭하고 New -> Module 을 선택하여 Gradle로 모듈을 만들어준다.
    
core 모듈은 service, batch에서 고통으로 사용될 모듈이다. 모듈을 추가시 setting.gradle에 해당 모듈이 자동으로 설정 되지만 없는 경우 'include 모듈명' 으로 추가해주면된다.

    rootProject.name = 'multi-module-ex'
    include 'module-core'
    include 'module-service'
    include 'module-batch'

### step 3. 프로젝트 세팅

루트 프로젝트의 build.gradle에서 하위 모듈의 gradle 세팅을 모두 설정하거나  각 모듈별로 build.gradle 세팅을 따로 설정할 수 있다.
 
아래 코드는 루트 프로젝트의 build.gradle에서 하위 모듈의 gradle 세팅을 모두 설정하는 방법이다.
 
 
       buildscript {
          ext {
              springBootVersion = '2.6.2'
          }
          repositories {
              mavenCentral()
          }
          dependencies {
              classpath "org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}"
              classpath "io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE"
          }
      }
      // 하위 모든 프로젝트 공통 세팅
      subprojects {
          apply plugin: 'java'
          apply plugin: 'idea'
          apply plugin: 'org.springframework.boot'
          apply plugin: 'io.spring.dependency-management'

          group 'org.example'
          version '1.0-SNAPSHOT'

          sourceCompatibility = '1.8'
          targetCompatibility = '1.8'
          compileJava.options.encoding = 'UTF-8'

          repositories {
              mavenCentral()
          }

          dependencies {
              compileOnly 'org.projectlombok:lombok'

              annotationProcessor 'org.projectlombok:lombok'
              annotationProcessor "org.springframework.boot:spring-boot-configuration-processor"

              testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0'
              testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0'
          }
          test {
              useJUnitPlatform()
          }
      }

      // 각 프로젝트에서만 사용하는 의존성 추가
      project(':module-core') {

          // core 에서는 bootjar로 패키징 할 필요가 없으므로 추가
          bootJar.enabled = false
          jar.enabled = true

          dependencies {
          }
      }

      project(':module-batch') {
          bootJar { enabled = true }
          jar { enabled = false }

          dependencies {
              compileOnly project(':module-core') // 컴파일 시 core모듈 가져옴
              implementation 'org.springframework.boot:spring-boot-starter-web'
          }
      }

      project(':module-service') {
          bootJar { enabled = true }
          jar { enabled = false }

          dependencies {
              compileOnly project(':module-core') 
              implementation 'org.springframework.boot:spring-boot-starter-web'
          }
      }
      
아래 코드는 core만 따로 세팅한 부분이다.
   
         dependencies {
          implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
          runtimeOnly 'com.h2database:h2'
        }


#### subprojects

subpojects 블록은 root의 하위에 있는 모든 모듈에 적용될 내용들을 작성하는 블록 이다.


### step 4. 실행

core모듈을 제외한 2모듈을 실행시키기 위해 각 모듈에 yaml파일을 만들어 포트를 각각 다르게 설정한 후 각 서버를 실행할 수 이도록 패키지를 만들고 Application 클래스 파일을 만들고 @SpringBootApplication 어노테이션을 통해 웹 어플리케이션이 실행 될 수 있도록 한다.

service모듈의 포트는 8081, batch모듈의 포트는 8080으로 설정했으며 각 모듈을 실행하고 localhost:8080, localhost:8081을 통해 서버가 동작하면 성공이다.

### step5. 공통모듈 사용하기

core모듈에 Member라는 객체를 생성해서 service모듈에서 사용할 수 있는지 확인해보자! 

com.bs.core.domain패키지를 만들고 Member 클래스 생성

      @Entity
      @Data
      public class Member {
          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private Long id;
          private String name;
          private int age;

          protected Member(){
          }

          @Builder
          public Member(Long id, String name, int age){
              this.id = id;
              this.name = name;
              this.age = age;
          }
      }
      
      
service모듈에서 Member객체를 사용해보자!


        import com.bs.core.domain.Member;

        @RestController
        @RequestMapping("api")
        public class ServiceController {

            @GetMapping("")
            public Member getMember(){
                Member member = Member.builder().age(10).name("홍길동").build();
                return member;
            }
        }

위에서 처럼 core 모듈의 Member 객체를 사용할 수 있다.


