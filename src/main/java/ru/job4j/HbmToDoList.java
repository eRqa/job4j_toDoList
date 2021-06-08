package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.models.Category;
import ru.job4j.models.Item;
import ru.job4j.models.User;

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
                session -> session.createQuery("SELECT item from Item item " +
                        "LEFT JOIN FETCH item.categories").list()
        );
    }

    @Override
    public List<Item> getCompleted() {
        return tx(
                session -> session.createQuery("SELECT item from Item item " +
                        "LEFT JOIN FETCH item.categories WHERE item.done = true").list()
        );
    }

    @Override
    public List<Category> getAllCategories() {
        return tx(
                session -> session.createQuery("from Category").list()
        );
    }

    @Override
    public Item findItemById(int id) {
        return tx(
                session -> session.get(Item.class, id)
        );
    }

    @Override
    public List<Category> findCategoriesByIds(Integer[] ids) {
        return tx(
                session -> {
                    var q = session.createQuery("FROM Category WHERE id IN (:ids)");
                    q.setParameterList("ids", ids);
                    return q.list();
                }
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return tx(
                session -> {
                    User result = null;
                    Query q = session.createQuery("from User WHERE email = :email");
                    q.setParameter("email", email);
                    List<User> foundedUsers = q.list();
                    if (foundedUsers.size() > 0) {
                        result = foundedUsers.get(0);
                    }
                    return result;
                }
        );
    }

    @Override
    public User saveUser(User user) {
        return tx(
                session -> {
                    session.save(user);
                    return user;
                }
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

    public static void main(String[] args) {
        List<Item> items = HbmToDoList.instOf().getAllTasks();
    }

}
