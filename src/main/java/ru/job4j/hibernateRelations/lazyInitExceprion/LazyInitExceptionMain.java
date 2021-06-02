package ru.job4j.hibernateRelations.lazyInitExceprion;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class LazyInitExceptionMain {
    public static void main(String[] args) {
        List<Brand> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
//            There was an exception : "failed to lazily initialize a collection of role:"
//            list = session.createQuery("from Brand").list();
            list = session.createQuery(
                    "select distinct brand from Brand brand join fetch brand.models"
            ).list();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Brand brand : list) {
            for (Model model : brand.getModel()) {
                System.out.println(model);
            }
        }
    }
}
