package ru.job4j.hibernateRelations.otherExamples;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class CandidatesMain {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate pavel = Candidate.of("Pavel", 1, 100500);
            Candidate victor = Candidate.of("Victor", 3, 100550);
            Candidate gena = Candidate.of("Gena", 7, 100);

            session.save(pavel);
            session.save(victor);
            session.save(gena);

            List<Candidate> allCandidates = session.createQuery("FROM Candidate", Candidate.class).list();
            System.out.println("===== All candidates =====");
            allCandidates.forEach(System.out::println);

            var q = session.createQuery("FROM Candidate WHERE id = :id", Candidate.class);
            q.setParameter("id", gena.getId());
            List<Candidate> candidatesById = q.getResultList();

            System.out.println("===== Candidate Gena by id =====");
            candidatesById.forEach(System.out::println);

            q = session.createQuery("FROM Candidate WHERE name = :name", Candidate.class);
            q.setParameter("name", pavel.getName());
            List<Candidate> candidatesByName = q.getResultList();

            System.out.println("===== Candidate Pavel by name =====");
            candidatesByName.forEach(System.out::println);

            System.out.println("===== update Gena =====");
            System.out.println("old values");
            System.out.println(gena.toString());
            session.createQuery("update Candidate c set c.experience = :newExp, c.salary = :newSalary where c.id = :cID")
                    .setParameter("newExp", 6)
                    .setParameter("newSalary", 100900)
                    .setParameter("cID", gena.getId())
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();

            session = sf.openSession();
            session.beginTransaction();

            System.out.println("new values");
            Candidate newGena = session.get(Candidate.class, gena.getId());
            System.out.println(newGena.toString());

            session.createQuery("delete from Candidate where id = :cID")
                    .setParameter("cID", gena.getId())
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
