package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.embeddables.AttendanceIdentity;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceIdentity> {
    @Query("SELECT a FROM Attendance a " +
            "WHERE a.attendanceIdentity.scheduleId = :lessonsId " +
            "AND a.student.id = :studentId " +
            "ORDER BY a.attendanceIdentity.date ASC")
    List<Attendance> findLessonAttendanceForStudent(@Param("lessonsId") int lessonsId, @Param("studentId") int studentId);
}
