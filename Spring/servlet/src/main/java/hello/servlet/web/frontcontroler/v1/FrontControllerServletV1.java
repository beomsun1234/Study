package hello.servlet.web.frontcontroler.v1;

import hello.servlet.web.frontcontroler.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroler.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroler.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "FrontControllerServletV1",urlPatterns = "/front-controller/v1/*")  //v1뒤에 어떤 게 들어와도 일단 무조건 FrontControllerServletV1 호출
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String,ControllerV1> controllerMap = new HashMap<>(); //매핑정보


    public FrontControllerServletV1(){ //생성자
        controllerMap.put("/front-controller/v1/members/new-form",new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        // /front-controller/v1/members -> MemberListControllerV1()
        String reqquestURI = request.getRequestURI();


        //다형성
        // ControllerV1 controllerV1 = new MemberFormControllerV1; 부모는 자식을 받아드릴수있다
        ControllerV1 controllerV1 = controllerMap.get(reqquestURI);
        if (controllerV1 == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controllerV1.process(request, response);
    }
}
