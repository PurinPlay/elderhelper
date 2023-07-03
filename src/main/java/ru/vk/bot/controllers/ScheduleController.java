package ru.vk.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vk.bot.models.Lessons;
import ru.vk.bot.models.Schedule;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.LessonsRepository;
import ru.vk.bot.repository.ScheduleRepository;
import ru.vk.bot.repository.StudentsRepository;

import java.sql.Time;

@Controller
public class ScheduleController {
    @Autowired
    LessonsRepository lessonsRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @GetMapping("/schedule")
    public String schedule(Model model){
        Iterable<Lessons> lessons = lessonsRepository.findAll();
        model.addAttribute("lessons", lessons);
        Iterable<Schedule> schedules = scheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        return "schedule";
    }
    @PostMapping("/schedule/edit")
    public String editSchedule(Model model, @RequestParam int scheduleList, @RequestParam int lesson,
                               @RequestParam(defaultValue = "") String professor, @RequestParam int DOTW,
                               @RequestParam String type, @RequestParam int week, @RequestParam String time){
        String status = "";
        Schedule schedule;
        try {
            Lessons targetLesson = lessonsRepository.findById(lesson).orElseThrow();
            Time time1 = new Time(Integer.parseInt(time.split(":")[0]), Integer.parseInt(time.split(":")[1]),0);
            if (scheduleList == -1){
                schedule = new Schedule(targetLesson, professor, DOTW, time1, type, week);
                status = "added";
            }else{
                schedule = new Schedule(scheduleList, targetLesson, professor, DOTW, time1, type, week);
                status = "updated";
            }
            scheduleRepository.save(schedule);
        }catch (Exception e){
            status = "failure";
        }
        return "redirect:/schedule?status="+status;
    }
    @PostMapping("/schedule/remove")
    public String removeSchedule(@RequestParam int scheduleList, Model model){
        String status = "";
        try {
            Schedule targetSchedule = scheduleRepository.findById(scheduleList).orElseThrow();
            scheduleRepository.delete(targetSchedule);
            status = "removed";
        }catch (Exception e){
            status = "failure";
        }
        return "redirect:/schedule?status="+status;
    }
}
