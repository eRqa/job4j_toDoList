package ru.job4j.hibernateRelations.carsAndOwners;

import javax.persistence.*;

@Entity
@Table(name = "engines")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

}
