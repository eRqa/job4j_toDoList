package ru.job4j.servelts;

import ru.job4j.HbmToDoList;
import ru.job4j.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RevertServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idToRevert = Integer.parseInt(req.getParameter("idToRevert"));
        Item foundedItem = HbmToDoList.instOf().findItemById(idToRevert);
        HbmToDoList.instOf().revertDone(foundedItem);
    }

}
