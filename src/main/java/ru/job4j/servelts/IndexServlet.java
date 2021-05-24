package ru.job4j.servelts;

import ru.job4j.HbmToDoList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tasks", HbmToDoList.instOf().getAllTasks());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
