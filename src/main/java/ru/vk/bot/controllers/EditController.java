package ru.vk.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vk.bot.models.Lessons;
import ru.vk.bot.models.Schedule;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.AttendanceRepository;
import ru.vk.bot.repository.LessonsRepository;
import ru.vk.bot.repository.ScheduleRepository;
import ru.vk.bot.repository.StudentsRepository;

@Controller
public class EditController {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    LessonsRepository lessonsRepository;
    @Autowired
    StudentsRepository studentsRepository;
    @GetMapping("/edit")
    public String edit(Model model){
        Iterable<Lessons> lessons = lessonsRepository.findAll();
        model.addAttribute("lessons", lessons);
        Iterable<Students> students = studentsRepository.findAll();
        model.addAttribute("students",students);
        return "edit";
    }
    @PostMapping("/edit/update")
    public String updateEdit(Model model){
        String status="";
        try {
            
        }catch (Exception e){
            status = "notFound";
        }
        return "redirect:edit?status="+status;
    }
}
