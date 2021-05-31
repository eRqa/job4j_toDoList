package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.models.Item;

import java.util.List;
import java.util.function.Function;

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
        return tx(
                session -> {
                    session.save(item);
                    return item;
                }
        );
    }

    @Override
    public boolean revertDone(Item item) {
        return tx(
                session -> {
                    item.setDone(!item.isDone());
                    session.update(item);
                    return item.isDone();
                }
        );
    }

    @Override
    public List<Item> getAllTasks() {
        return tx(
                session -> session.createQuery("from Item").list()
        );
    }

    @Override
    public List<Item> getCompleted() {
        return tx(
                session -> session.createQuery("from Item WHERE done = true").list()
        );
    }

    @Override
    public Item findItemById(int id) {
        return tx(
                session -> session.get(Item.class, id)
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
