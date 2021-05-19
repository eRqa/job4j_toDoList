package ru.job4j.servelts;

import ru.job4j.HbmToDoList;
import ru.job4j.models.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("tasks", HbmToDoList.instOf().getAllTasks());
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, "123", new Date(), false));
        itemList.add(new Item(2, "345", new Date(), true));
        req.setAttribute("tasks", itemList);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
