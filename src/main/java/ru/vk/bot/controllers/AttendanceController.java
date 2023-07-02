package ru.vk.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vk.bot.models.Lessons;
import ru.vk.bot.repository.LessonsRepository;
import ru.vk.bot.repository.StudentsRepository;

@Controller
public class AttendanceController {
    @Autowired
    LessonsRepository lessonsRepository;
    @Autowired
    StudentsRepository studentsRepository;

    @GetMapping("/attendance")
    public String schedule(Model model){
        Iterable<Lessons> lessons = lessonsRepository.findAll();
        model.addAttribute("lessons", lessons);
        return "attendance";
    }
}