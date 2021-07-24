package hello.servlet.web.frontcontroler.V5;

import hello.servlet.web.frontcontroler.ModelView;
import hello.servlet.web.frontcontroler.MyView;
import hello.servlet.web.frontcontroler.V5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroler.V5.adapter.ControllerV4HandlerAdapter;
import hello.servlet.web.frontcontroler.v3.ControllerV3;
import hello.servlet.web.frontcontroler.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroler.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroler.v3.controller.MemberSaveControllerV3;
import org.springframework.http.HttpMethod;

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

@WebServlet(name="frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    private Map<String, Object> hanlderMappintMap = new HashMap<>();
    private final List<MyhandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5(){
        inithanlderMappintMap();
        inithandlerAdapters();

    }

    private void inithanlderMappintMap(){
        hanlderMappintMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        hanlderMappintMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        hanlderMappintMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        //------------------------------------------------------------------------------------
        hanlderMappintMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV3());
        hanlderMappintMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV3());
        hanlderMappintMap.put("/front-controller/v5/v4/members", new MemberListControllerV3());
    }

    private void inithandlerAdapters(){
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*  String requestURI = request.getRequestURI();
            Object handler = hanlderMappintMap.get(requestURI);  */

        //MemberFormControllerV3
        Object handler = getHandler(request);
        if(handler == null){
            response.setStatus(HttpServletResponse.SC_FOUND);
            return;
        }
        //ControllerV3HandelerAdapter
        MyhandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);

        //new-form
        String viewName = mv.getViewName(); //-> 논리이름만 얻어옴 new-form
        MyView view = viewResolver(viewName);

        Map<String, String> test = new HashMap<>();
        view.rander(mv.getModel(),request,response);
    }

    private MyhandlerAdapter getHandlerAdapter(Object handler) {
        //MemberControllerV3
        for (MyhandlerAdapter adapter : handlerAdapters){
            if (adapter.suppoerts(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("handeler adapter를 찾을 수 없습니니다"+handler);
   }

    private Object getHandler(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return hanlderMappintMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp"); //뷰반환

    }
}
