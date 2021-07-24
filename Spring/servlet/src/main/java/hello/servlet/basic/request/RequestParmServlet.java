package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//-> baseurl/request-param?name=kim&age=20

// 단일일경우 getParameter  복수일경우 getParameterValues

@WebServlet(name="requestParamServlet",urlPatterns = "/request-param")
public class RequestParmServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체파라미터 조회] ---strat---");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName-> System.out.println(paramName+ "=" +request.getParameter(paramName)));
        System.out.println("[전체파라미터 조회] ---end---");
        System.out.println();
        //-------------------------------------------------
        System.out.println("[단일파라미터 조회] ---start---");
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println("[단일파라미터 조회] ---end---");
        System.out.println();
        //-------------------
        System.out.println("[이름이 같은 복수 파라미터 조회] -start");
        String[] usernames = request.getParameterValues("username");
        for (String name: usernames) {
            System.out.println("name = " + name);
        }
        response.getWriter().write("ok");


    }
}
