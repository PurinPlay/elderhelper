package ru.vk.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.StudentsRepository;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentsRepository studentsRepository;
    public List<Students> findAllSortedByFullName(){
        return studentsRepository.findAll(Sort.by(Sort.Direction.ASC, "fullName"));
    }
}
