package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.embeddables.AttendanceIdentity;
import ru.vk.bot.repository.templates.DateSchedule;

import java.sql.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceIdentity> {
    @Query("SELECT a FROM Attendance a " +
            "JOIN FETCH a.schedule s " +
            "JOIN FETCH a.student st " +
            "WHERE s.lesson.id = :lessonId " +
            "AND st.id = :studentId " +
            "ORDER BY a.attendanceIdentity.date ASC")
    List<Attendance> findLessonAttendanceForStudent(@Param("lessonId") int lessonId, @Param("studentId") int studentId);
    @Query("SELECT DISTINCT a.attendanceIdentity.date " +
            "FROM Attendance a " +
            "JOIN a.schedule s " +
            "WHERE s.lesson.id = :lessonId " +
            "ORDER BY a.attendanceIdentity.date ASC")
    List<Date> findDatesByLessonId(@Param("lessonId") int lessonId);
    @Query("SELECT DISTINCT new ru.vk.bot.repository.templates.DateSchedule(a.attendanceIdentity.date, a.attendanceIdentity.scheduleId) FROM Attendance a")
    List<DateSchedule> findDistinctLessonDatesAndScheduleIds();
}
