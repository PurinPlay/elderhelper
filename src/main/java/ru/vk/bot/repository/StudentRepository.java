package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vk.bot.models.Students;

public interface StudentRepository extends JpaRepository<Students, Integer> {
}
