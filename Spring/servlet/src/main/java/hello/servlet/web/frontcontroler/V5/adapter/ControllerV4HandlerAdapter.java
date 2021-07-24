package hello.servlet.web.frontcontroler.V5.adapter;

import hello.servlet.web.frontcontroler.ModelView;
import hello.servlet.web.frontcontroler.V5.MyhandlerAdapter;
import hello.servlet.web.frontcontroler.v4.ControllerV4;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyhandlerAdapter {
    @Override
    public boolean suppoerts(Object handeler) {
        return (handeler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;
        Map<String,String> parMap = createParamMap(request);
        HashMap<String,Object> model = new HashMap<>();

        String viewName = controller.process(parMap, model);
        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName->paramMap.put(paramName,request.getParameter(paramName)));
        return paramMap;
    }
}
