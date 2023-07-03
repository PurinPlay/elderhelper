package ru.vk.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vk.bot.models.Students;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer> {
}
