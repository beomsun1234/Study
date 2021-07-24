package hello.servlet.web.frontcontroler.v3;

import hello.servlet.web.frontcontroler.ModelView;
import hello.servlet.web.frontcontroler.MyView;
import hello.servlet.web.frontcontroler.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroler.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroler.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name="frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String,ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3(){
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paraMap
        Map<String, String> paramMap = createParamMap(request);
        System.out.println("paramMap = " + paramMap);
        ModelView mv = controller.process(paramMap);
        //new-form
        String viewName = mv.getViewName(); //-> 논리이름만 얻어옴 new-form
        MyView view = viewResolver(viewName);

        Map<String, String> test = new HashMap<>();
        view.rander(mv.getModel(),request,response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp"); //뷰반환
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName->paramMap.put(paramName,request.getParameter(paramName)));
        return paramMap;
    }
}
