package ru.job4j.servelts;

import org.json.JSONArray;
import ru.job4j.HbmToDoList;
import ru.job4j.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Item> items = HbmToDoList.instOf().getAllTasks();
        PrintWriter writer = resp.getWriter();
        writer.print(new JSONArray(items).toString());
        writer.flush();
//        req.setAttribute("tasks", HbmToDoList.instOf().getAllTasks());
//        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HbmToDoList.instOf().add(
                new Item(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        new Date(),
                        false)
                );
        resp.sendRedirect(req.getContextPath() + "/index.do");
    }
}
