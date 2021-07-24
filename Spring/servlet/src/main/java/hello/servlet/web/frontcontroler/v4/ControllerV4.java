package hello.servlet.web.frontcontroler.v4;

import hello.servlet.web.frontcontroler.ModelView;

import java.util.Map;

public interface ControllerV4 {

    String process(Map<String, String> paramMap,  Map<String, Object> model);

}
