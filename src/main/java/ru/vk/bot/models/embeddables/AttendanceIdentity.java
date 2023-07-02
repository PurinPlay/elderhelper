package ru.vk.bot.models.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Date;

@Embeddable
public class AttendanceIdentity implements Serializable {
    @NotNull
    @Column(name = "student_id")
    private int studentId;
    @NotNull
    @Column(name = "schedule_id")
    private int scheduleId;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "lesson_date")
    private Date date;


    public AttendanceIdentity(){}
    public AttendanceIdentity(int studentId, int scheduleId, Date date) {
        this.studentId = studentId;
        this.scheduleId = scheduleId;
        this.date = date;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttendanceIdentity that = (AttendanceIdentity) o;

        if (studentId != that.studentId) return false;
        if (scheduleId != that.scheduleId) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + scheduleId;
        result = 31 * result + date.hashCode();
        return result;
    }
}
