package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.models.Item;

import java.util.List;

public class HbmToDoList implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new HbmToDoList();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean revertDone(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        item.setDone(!item.isDone());
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item.isDone();
    }

    @Override
    public List<Item> getAllTasks() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> getCompleted() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item WHERE Item.done").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item findItemById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

}
