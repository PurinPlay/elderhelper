package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vk.bot.models.Students;

public interface StudentsRepository extends JpaRepository<Students, Integer> {
}
