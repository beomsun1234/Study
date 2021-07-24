package hello.servlet.web.frontcontroler.v4;

import hello.servlet.web.frontcontroler.ModelView;
import hello.servlet.web.frontcontroler.MyView;
import hello.servlet.web.frontcontroler.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroler.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroler.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroler.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroler.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroler.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServlet4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServlet4 extends HttpServlet {

    Map<String,ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServlet4(){
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap,model);
        MyView view = viewResolver(viewName);

        view.rander(model,request,response);

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
