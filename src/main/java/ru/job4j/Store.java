package ru.job4j;

import ru.job4j.models.Category;
import ru.job4j.models.Item;
import ru.job4j.models.User;

import java.util.List;

public interface Store {

    Item add(Item item);

    List<Item> getAllTasks();

    List<Item> getCompleted();

    List<Category> getAllCategories();

    Item findItemById(int id);

    List<Category> findCategoriesByIds(Integer[] ids);

    User findUserByEmail(String email);

    User saveUser(User user);

    boolean revertDone(Item item);

}
