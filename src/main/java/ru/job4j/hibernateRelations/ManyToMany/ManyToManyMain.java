package ru.job4j.hibernateRelations.ManyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ManyToManyMain {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

//            Book one = Book.of("Преступление и наказание");
//            Book two = Book.of("Братья Карамазовы");
//            Book three = Book.of("Американская трагедия");
//
//            Author first = Author.of("Ф.М. Достоевский");
//            first.getBooks().add(one);
//            first.getBooks().add(two);
//
//            Author second = Author.of("Теодор Дрейзер");
//            second.getBooks().add(three);
//
//            session.persist(first);
//            session.persist(second);
//
//            Author dostoevskiy = session.get(Author.class, 1);
//            session.remove(dostoevskiy);
//
//            session.getTransaction().commit();
//            session.close();


            Book someBook = Book.of("Some book");

            Author someAuthor = Author.of("Some Author");
            Author someAuthor2 = Author.of("Some Author2");
            someAuthor.getBooks().add(someBook);
            someAuthor2.getBooks().add(someBook);

            session.persist(someAuthor);
            session.persist(someAuthor2);

            session.remove(someAuthor);

            session.getTransaction().commit();
            session.close();

        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
