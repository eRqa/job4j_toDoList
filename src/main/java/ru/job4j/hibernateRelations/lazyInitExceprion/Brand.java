package ru.job4j.hibernateRelations.lazyInitExceprion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Model> models;

    public static Brand of(String name) {
        Brand b = new Brand();
        b.name = name;
        return b;
    }

    public List<Model> getModel() {
        return models;
    }

    public void setModel(List<Model> model) {
        this.models = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
