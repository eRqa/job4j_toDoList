package ru.job4j.servelts;

import ru.job4j.HbmToDoList;
import ru.job4j.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class ItemAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //toDo - Разобраться. Возможно, конструкицю преобразования String в Integer можно упростить
        String[] stringCategoryIds = req.getParameterValues("categoryIds[]");
        int[] intCategoryIds = Arrays.stream(stringCategoryIds).mapToInt(Integer::parseInt).toArray();
        Integer[] categoryIds = IntStream.of(intCategoryIds).boxed().toArray(Integer []::new);

        List<Category> categories = HbmToDoList.instOf().findCategoriesByIds(categoryIds);
        HttpSession session = req.getSession();
        HbmToDoList.instOf().add(
                new Item(0,
                        req.getParameter("descriptionNewTask"),
                        (User) session.getAttribute("user"),
                        new Date(System.currentTimeMillis()),
                        false,
                        categories)
        );
    }
}
