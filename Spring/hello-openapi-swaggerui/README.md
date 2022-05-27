## Swagger-Ui/OpenAPI 적용


### OpenApi 사양(openapi.json)

OpenAPI 사양은 API의 기능을 설명하는 문서입니다. 이 문서는 OpenAPI 흐름의 핵심 부분이며, SwaggerUI와 같은 도구를 구동하는 데 사용됩니다. 기본적으로 이름은 openapi.json입니다.


### Swagger UI

Swagger UI는 생성된 OpenAPI 사양을 사용하여 서비스에 대한 정보를 제공하는 웹 기반 UI를 제공합니다


### springboot에 swagger를 적용해보자

아래와 같이 프로젝트 build.gradle에 Swagger 관련 dependency를 추가해주자.


      dependencies {
          implementation "io.springfox:springfox-boot-starter:3.0.0"
      }

SwaggerConfig클래스를 생성, Docket는 Swagger 설정의 핵심이 되는 문서화 객체입니다.

        @EnableSwagger2
        @Configuration
        public class SwaggerConfig {

          @Bean
          public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.any())
              .paths(PathSelectors.any())
              .build();
          }
        }
   
apis는 Swagger API 문서로 만들기 원하는 basePackage 경로

    RequestHandlerSelectors.any()
    RequestHandlerSelectors.any()는 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출한다. 

    특정패기지 하위의 api만을 적용할 경우 packageName에 Swagger를 적용할 클래스의 package 명을 적어준다.
    RequestHandlerSelectors.basePackage(String packageName)
    


paths는 path 조건에 해당하는 API를 찾아서 문서화합니다. 
 
    PathSelectors.any()
    해당 package 하위에 있는 모든 url에 적용시킨다. /api/**로시작되는 부분만 적용해주고싶다면 PathSelectors.ant("/api/**")

### OpenAPI Spec(.json) 추출하기
  
  프로젝트를 구동시키고 http://localhost:[port]/swagger-ui.html 접속하면 왼쪽 상단의 /.../v2/api-docs 링크를 클릭하면 json형식으로 OpenAPI Spec 추출할 수 있다. 해당 데이터를 복사하고
  .json형식으로 저장한다(위치는 상관없으나 docker swagger-ui실행시 해당 json을 사용하므로 잘 보관해야한다.). 아래는 추출한 OpenAPI Spec이다.
  
  
          {
          "swagger": "2.0",
          "info": {
            "description": "Api Documentation",
            "version": "1.0",
            "title": "Api Documentation",
            "termsOfService": "urn:tos",
            "contact": {},
            "license": {
              "name": "Apache 2.0",
              "url": "http://www.apache.org/licenses/LICENSE-2.0"
            }
          },
          "host": "localhost:8080",
          "basePath": "/",
          "tags": [{
            "name": "calculator-controller",
            "description": "Calculator Controller"
          }],
          "paths": {
            "/api/addition": {
              "get": {
                "tags": ["calculator-controller"],
                "summary": "addition",
                "operationId": "additionUsingGET",
                "produces": ["*/*"],
                "parameters": [{
                  "name": "num1",
                  "in": "query",
                  "description": "num1",
                  "required": true,
                  "type": "integer",
                  "format": "int32"
                },
                  {
                    "name": "num2",
                    "in": "query",
                    "description": "num2",
                    "required": true,
                    "type": "integer",
                    "format": "int32"
                  }
                ],
                "responses": {
                  "200": {
                    "description": "OK",
                    "schema": {
                      "type": "integer",
                      "format": "int32"
                    }
                  },
                  "401": {
                    "description": "Unauthorized"
                  },
                  "403": {
                    "description": "Forbidden"
                  },
                  "404": {
                    "description": "Not Found"
                  }
                },
                "deprecated": false
              }
            },
            "/api/division": {
              "get": {
                "tags": [
                  "calculator-controller"
                ],
                "summary": "division",
                "operationId": "divisionUsingGET",
                "produces": ["*/*"],
                "parameters": [{
                  "name": "num1",
                  "in": "query",
                  "description": "num1",
                  "required": true,
                  "type": "integer",
                  "format": "int32"
                }, {
                  "name": "num2",
                  "in": "query",
                  "description": "num2",
                  "required": true,
                  "type": "integer",
                  "format": "int32"
                }],
                "responses": {
                  "200": {
                    "description": "OK",
                    "schema": {
                      "type": "integer",
                      "format": "int32"
                    }
                  },
                  "401": {
                    "description": "Unauthorized"
                  },
                  "403": {
                    "description": "Forbidden"
                  },
                  "404": {
                    "description": "Not Found"
                  }
                },
                "deprecated": false
              }
            },
            "/api/multiplication": {
              "get": {
                "tags": ["calculator-controller"],
                "summary": "multiplication",
                "operationId": "multiplicationUsingGET",
                "produces": ["*/*"],
                "parameters": [{
                  "name": "num1",
                  "in": "query",
                  "description": "num1",
                  "required": true,
                  "type": "integer",
                  "format": "int32"
                }, {
                  "name": "num2",
                  "in": "query",
                  "description": "num2",
                  "required": true,
                  "type": "integer",
                  "format": "int32"
                }],
                "responses": {
                  "200": {
                    "description": "OK",
                    "schema": {
                      "type": "integer",
                      "format": "int32"
                    }
                  },
                  "401": {
                    "description": "Unauthorized"
                  },
                  "403": {
                    "description": "Forbidden"
                  },
                  "404": {
                    "description": "Not Found"
                  }
                },
                "deprecated": false
              }
            },
            "/api/subtraction": {
              "get": {
                "tags": ["calculator-controller"],
                "summary": "subtraction",
                "operationId": "subtractionUsingGET",
                "produces": ["*/*"],
                "parameters": [{
                  "name": "num1",
                  "in": "query",
                  "description": "num1",
                  "required": true,
                  "type": "integer",
                  "format": "int32"
                }, {
                  "name": "num2",
                  "in": "query",
                  "description": "num2",
                  "required": true,
                  "type": "integer",
                  "format": "int32"
                }],
                "responses": {
                  "200": {
                    "description": "OK",
                    "schema": {
                      "type": "integer",
                      "format": "int32"
                    }
                  },
                  "401": {
                    "description": "Unauthorized"
                  },
                  "403": {
                    "description": "Forbidden"
                  },
                  "404": {
                    "description": "Not Found"
                  }
                },
                "deprecated": false
              }
            }
          }
        }
        
### 도커를 이용해 추출한 파일을 Swagger UI로 전송
 
2개의 어플리케이션을 문서화 하기위해서 2개의 서버를 구동시켜야하지만 SwaggerUI를 사용하면 SwaggerUI가 동작하는 서버에서 OpenAPI 스펙에 맞춘 문서만 있으면 모두 표현이 가능합니다.

아래 명령어를 통해  swagger-ui 이미즈를 다운받고 

     docker pull swaggerapi/swagger-ui
     
     
아래 명령어를 통해 swagger-ui 서버를 구동시킵니다.

      docker run -d -p 80:8080 -e SWAGGER_JSON=/mnt/api.json -v C:\Users\park\doc:/mnt swaggerapi/swagger-ui
  
 
localhost:80 으로 접속하면 기존에 어플레케이션을 구동했을때 동일하게 보입니다!!
 
 
### swagger ui에 여러개의 OpenAPI Spec 실행방법

          docker run -d -p 80:8080 -e URLS_PRIMARY_NAME=Swagger -e URLS="[{ url: 'docs/api.json', name: 'Swagger' }, { url: 'docs/api2.json', name: 'Swagger2' }]" -v C:\Users\uuhu\park\doc:/usr/share/nginx/html/docs/ swaggerapi/swagger-ui
 
 
 
### 트러블 슈팅

#### docker오류
아래 명령어를 통해 swagger-ui 서버 구동시
  
    docker run -d -p 80:8080 -e SWAGGER_JSON=/mnt/api.json -v C:\Users\uuhu\park\doc:/mnt swaggerapi/swagger-ui
  
OpenAPI Spec가 저장된 json파일의 경로를 찾지못하는 오류가 발생했었다... 구글링을 통해 해결할 수 있었다. docker desktop - Settings - Resources - FILE SHARING에 컨테이너가 마운트할 수 있는 경로를 설정해주면서 해결할 수 있었다.

#### swagger


swagger 3.0.0으로 설정한 후 프로젝트를 구동시키는데 org.springframework.context.ApplicationContextException: Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NoClassDefFoundError: javax/validation/constraints/NotBlank 이런 에러가 발생했따.

감사하게도 구글링을 통해 해당 문제를 해결할 수 있었다. Spring boot 2.6버전 이후에 spring.mvc.pathmatch.matching-strategy 값이 ant_apth_matcher에서 path_pattern_parser로 변경되면서 몇몇 라이브러리(swagger포함)에 오류가 발생하며

application.yml 에 아래 설정을 추가하면 오류가 발생 하지 않는다.


spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher


 [출저](https://www.inflearn.com/questions/230160) 
 
  
  
  
