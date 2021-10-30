package cn.ynmz.travel.web.servlet;

import cn.ynmz.travel.domain.Category;
import cn.ynmz.travel.service.CategoryService;
import cn.ynmz.travel.service.impl.CategroyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categroy/*")
public class CategroyServlet extends BaseServlet {
    private CategoryService service = new CategroyServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> all = service.findAll();
        //序列化json返回
        writeValue(all, response);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
