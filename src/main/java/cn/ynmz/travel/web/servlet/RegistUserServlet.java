package cn.ynmz.travel.web.servlet;

import cn.ynmz.travel.domain.ResultInfo;
import cn.ynmz.travel.domain.User;
import cn.ynmz.travel.service.UserService;
import cn.ynmz.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//删除session中的验证码
        if (checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo inof=new ResultInfo();
            inof.setFlag(false);
            inof.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String inofs = mapper.writeValueAsString(inof);
            //将json数据字节流写会客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(inofs);
            return;
        }else {
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user,parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            UserService service=new UserServiceImpl();
            boolean flag=service.regist(user);
            ResultInfo inof=new ResultInfo();

            if (flag){
                //注册成功
                inof.setFlag(true);

            }else {
                //注册失败
                inof.setFlag(false);
                inof.setErrorMsg("该用户名已经存在，注册失败");

            }
            //将info对象序列化为json，写回客户端
            ObjectMapper mapper = new ObjectMapper();
            String inofs = mapper.writeValueAsString(inof);
            //将json数据字节流写会客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(inofs);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
