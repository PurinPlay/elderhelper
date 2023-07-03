package ru.vk.bot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import ru.vk.bot.embeddables.AttendanceIdentity;

@Entity
@Table(name = "attendance")
public class Attendance {

    @EmbeddedId
    private AttendanceIdentity attendanceIdentity;
    @NotNull
    @Column(name = "is_visited")
    private boolean isVisited;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("scheduleId")
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Students student;

    public Attendance(AttendanceIdentity attendanceIdentity, boolean isVisited, Schedule schedule, Students student) {
        this.attendanceIdentity = attendanceIdentity;
        this.isVisited = isVisited;
        this.schedule = schedule;
        this.student = student;
    }

    public Attendance() {
    }

    public AttendanceIdentity getAttendanceIdentity() {
        return attendanceIdentity;
    }

    public void setAttendanceIdentity(AttendanceIdentity attendanceIdentity) {
        this.attendanceIdentity = attendanceIdentity;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }
}
