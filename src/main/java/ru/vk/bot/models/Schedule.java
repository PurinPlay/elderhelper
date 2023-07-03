package ru.vk.bot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Set;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lessons lesson;
    private String professor;
    @NotNull
    @Column(name = "day_of_the_week")
    private int dayOfTheWeek;
    @NotNull
    private Time time;
    @NotNull
    private String type;
    @NotNull
    private int week;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule")
    private Set<Attendance> attendances;
    public Schedule(){}
    public Schedule(int id, Lessons lesson, String professor, int dayOfTheWeek, Time time, String type, int week) {
        this.id = id;
        this.lesson = lesson;
        this.professor = professor;
        this.time = time;
        this.type = type;
        this.week = week;
    }

    public Schedule(Lessons lesson, String professor, int dayOfTheWeek, Time time, String type, int week) {
        this.lesson = lesson;
        this.professor = professor;
        this.dayOfTheWeek = dayOfTheWeek;
        this.time = time;
        this.type = type;
        this.week = week;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lessons getLesson() {
        return lesson;
    }

    public void setLesson(Lessons lesson) {
        this.lesson = lesson;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }
}
