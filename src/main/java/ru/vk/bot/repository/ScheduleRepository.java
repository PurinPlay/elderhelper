package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vk.bot.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
