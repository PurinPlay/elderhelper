package ru.vk.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vk.bot.embeddables.AttendanceIdentity;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.models.Schedule;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.AttendanceRepository;
import ru.vk.bot.repository.ScheduleRepository;
import ru.vk.bot.repository.StudentsRepository;

import java.io.Console;

import java.sql.Date;

@Controller
public class EditController {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    StudentsRepository studentsRepository;
    @GetMapping("/edit")
    public String edit(Model model){
        Iterable<Schedule> schedule = scheduleRepository.findAll();
        model.addAttribute("schedules", schedule);
        Iterable<Students> students = studentsRepository.findAll();
        model.addAttribute("students",students);
        return "edit";
    }
    @PostMapping("/edit/update")
    public String updateEdit(Model model, @RequestParam Date date, @RequestParam int student, @RequestParam int schedule, @RequestParam(defaultValue = "false") boolean isVisited){
        String status="";
        try {
            AttendanceIdentity identity = new AttendanceIdentity(student, schedule, date);
            Schedule targetSchedule = scheduleRepository.findById(schedule).orElseThrow();
            Students targetStudent = studentsRepository.findById(student).orElseThrow();
            if(attendanceRepository.existsById(identity)){
                attendanceRepository.save(new Attendance(identity, targetSchedule, targetStudent, isVisited));
                status="edited";
            }else{
                status="notFound";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            status = "failure";
        }
        return "redirect:/edit?status="+status;
    }
}
