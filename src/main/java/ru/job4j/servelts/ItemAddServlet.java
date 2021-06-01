package ru.job4j.servelts;

import ru.job4j.HbmToDoList;
import ru.job4j.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ItemAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HbmToDoList.instOf().add(
                new Item(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("descriptionNewTask"),
                        new Date(),
                        false)
        );
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
