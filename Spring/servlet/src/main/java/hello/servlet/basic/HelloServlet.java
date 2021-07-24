package hello.servlet.basic;

import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello") // 네임은 아무거나 지정, /hello 가 입력되면 밑에 클래스 실행됨
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("req = " + req);
        System.out.println("resp = " + resp);

        //?nmae = kim -> 쿼리파라미터

        //서블릿 요청
        String username = req.getParameter("username");
        System.out.println("username = " + username);

        //서블릿 응답
        resp.setContentType("text/pain");
        resp.setContentType("utf-8");
        resp.getWriter().write("hell"+username);

    }
}
