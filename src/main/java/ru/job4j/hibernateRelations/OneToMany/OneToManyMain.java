package ru.job4j.hibernateRelations.OneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class OneToManyMain {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

            Session session = sf.openSession();
            session.beginTransaction();

            //CASE 1 START
//            Model jetta = Model.of("Jetta");
//            Model polo = Model.of("Polo");
//
//            Brand vw = Brand.of("Volkswagen");
//            vw.getModels().add(jetta);
//            vw.getModels().add(polo);
//
//            session.save(vw);
            //CASE 1 END

            //CASE 2 START
            Model jetta = Model.of("Jetta");
            session.save(jetta);

            Model polo = Model.of("Polo");
            session.save(polo);

            Model passat = Model.of("Passat");
            session.save(passat);

            Model golf = Model.of("Golf");
            session.save(golf);

            Model arteon = Model.of("Arteon");
            session.save(arteon);

            Brand vw = Brand.of("Volkswagen");
            vw.addModel(session.load(Model.class, jetta.getId()));
            vw.addModel(session.load(Model.class, polo.getId()));
            vw.addModel(session.load(Model.class, passat.getId()));
            vw.addModel(session.load(Model.class, golf.getId()));
            vw.addModel(session.load(Model.class, arteon.getId()));

            session.save(vw);
            //CASE 2 END

            session.getTransaction().commit();
            session.close();

        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
