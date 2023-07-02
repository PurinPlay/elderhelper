package ru.vk.bot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "students")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private int id;
    @Column(name = "full_name")
    @Size(max = 100)
    @NotNull
    private String fullName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<Attendance> attendances;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Students(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
    public Students(String fullName) {
        this.fullName = fullName;
    }
    public Students() {
    }
}
