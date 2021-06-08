package ru.job4j.servelts;

import org.json.JSONArray;
import ru.job4j.HbmToDoList;
import ru.job4j.models.Category;
import ru.job4j.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CategoriesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories;
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        categories = HbmToDoList.instOf().getAllCategories();
        PrintWriter writer = resp.getWriter();
        writer.print(new JSONArray(categories).toString());
        writer.flush();
    }
}
