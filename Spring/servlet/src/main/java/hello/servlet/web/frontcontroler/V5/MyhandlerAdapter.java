package hello.servlet.web.frontcontroler.V5;

import hello.servlet.web.frontcontroler.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyhandlerAdapter {
    boolean suppoerts(Object handeler);

    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
