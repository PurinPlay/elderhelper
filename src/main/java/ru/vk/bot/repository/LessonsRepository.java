package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vk.bot.models.Lessons;

public interface LessonsRepository extends JpaRepository<Lessons, Integer> {
}
