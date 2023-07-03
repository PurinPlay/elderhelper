package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.embeddables.AttendanceIdentity;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceIdentity> {
}
