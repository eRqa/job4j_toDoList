package ru.job4j.servelts;

import ru.job4j.HbmToDoList;
import ru.job4j.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hereERERE");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = HbmToDoList.instOf().findUserByEmail(email);
        if (user == null) {
            System.out.println("1");
            User newUser = new User(0, name, email, password);
            HbmToDoList.instOf().saveUser(newUser);
            resp.sendRedirect(req.getContextPath() + "/login.do");
        } else {
            System.out.println("2");
            req.setAttribute("error", "Пользователь с таким E-Mail уже существует");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

}
