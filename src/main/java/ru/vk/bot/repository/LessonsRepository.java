package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vk.bot.models.Lessons;

@Repository
public interface LessonsRepository extends JpaRepository<Lessons, Integer> {
}
