package hello.Springmvc.basic.requestmapping;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Slf4j
@RestController
public class MappingController {



    @RequestMapping(value = {"/hello-basic","/hello-go"},method = RequestMethod.GET) //or조건
    public String requestMapping(){
        log.info("hellobasic");
        return "ok";
    }
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1(){
        log.info("MappingGetV1");
        return "ok";
    }


    /*
      *편라한 축약 애노테이션
      * @GETMapping
      * @PostMapping
      * @PutMapping
      * @DeleteMapping
      * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetv2(){
        log.info("mapping-get-v2");
        return "ok";
    }

    /* PathVariable 사용
    * 변수명이 같으면 생략 가능
    * @PathVariable("userId") String userId -> @PathVariable userId
    * @RequestMapping 은 URL 경로를 템플릿화 할 수 있는데, @PathVariable 을 사용하면 매칭 되는 부분을
    편리하게 조회할 수 있다.
    @PathVariable 의 이름과 파라미터 이름이 같으면 생략할 수 있다.
     */
//    @GetMapping("/mapping/{userId}")
//    public String mappingPath(@PathVariable String userId){
//        log.info("mappingPath usrrId={}",userId);
//        return userId;
//
//    }
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId){
        log.info("mappingPath usrrId={}",userId);
        return userId;

    }

    @GetMapping("/mapping/{userId}/orders/{oreders}")
    public String mappingPath(@PathVariable String userId,
                              @PathVariable Long oreders){
        log.info("mappingPath usrrId={},order={}",userId,oreders);
        return userId;
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"} or뜻
     *
     * 특정파라미터가 오면 출력된다다
     * and 조건임
     * */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }
    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     * consumes = 소비하는 입장 요청에 컨텐츠타입을 소비하므로 consumes라 불린다
     */

    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }










}
