package hello.servlet.web.frontcontroler.V5.adapter;

import hello.servlet.web.frontcontroler.ModelView;
import hello.servlet.web.frontcontroler.V5.MyhandlerAdapter;
import hello.servlet.web.frontcontroler.v3.ControllerV3;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyhandlerAdapter {
    @Override
    public boolean suppoerts(Object handeler) {
        return (handeler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        //MemberFormControllerV3
        ControllerV3 controller = (ControllerV3) handler; //기본 데이터형에서의 캐스팅은 원칙적으로 데이터손실을 막고자 한다.

        Map<String,String> parMap = createParamMap(request);
        ModelView mv = controller.process(parMap);

        return mv;
    }
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName->paramMap.put(paramName,request.getParameter(paramName)));
        return paramMap;
    }


}
