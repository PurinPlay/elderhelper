package ru.vk.bot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "lessons")
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private int id;
    @Size(max = 100)
    @NotNull
    @Column(unique=true)
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Schedule> ob;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Lessons(String title) {
        this.title = title;
    }
    public Lessons(int id, String title) {
        this.id = id;
        this.title = title;
    }
    public Lessons() {
    }
}
