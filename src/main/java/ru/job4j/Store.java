package ru.job4j;

import ru.job4j.models.Item;

import java.util.List;

public interface Store {

    Item add(Item item);

    List<Item> getAllTasks();

    List<Item> getCompleted();

    Item findItemById(int id);

    boolean revertDone(Item item);

}
