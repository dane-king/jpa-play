package com.daneking.jpaplay.student;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Student {
    @Id
    private long id;

    private String name;

    public Student(){

    }
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

