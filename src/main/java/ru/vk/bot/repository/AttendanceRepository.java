package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.models.embeddables.AttendanceIdentity;

public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceIdentity> {
}
