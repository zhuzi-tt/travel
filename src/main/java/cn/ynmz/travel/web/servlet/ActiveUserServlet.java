package cn.ynmz.travel.web.servlet;

import cn.ynmz.travel.service.UserService;
import cn.ynmz.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code!=null){
            UserService service=new UserServiceImpl();
            boolean flag=service.active(code);

            //判断激活
            if (flag){
                //激活成功
                response.sendRedirect("http://localhost/travel/active_ok.html");

            }else {
                //激活失败
                response.sendRedirect("http://localhost/travel/active_false.html");


            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
