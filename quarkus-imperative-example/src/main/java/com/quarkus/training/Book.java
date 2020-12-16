package com.quarkus.training;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Book extends PanacheEntity {
    public String title;
    public String description;
    public String author;
}