package ru.vk.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vk.bot.models.Lessons;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.LessonsRepository;

@Controller
public class LessonsController {
    @Autowired
    LessonsRepository lessonsRepository;
    @GetMapping("/lesson")
    public String lesson(Model model){
        Iterable<Lessons> lessons = lessonsRepository.findAll();
        model.addAttribute("lessons", lessons);
        return "lesson";
    }
    @PostMapping("/lesson/edit")
    public String addLesson(@RequestParam int lesson, @RequestParam String title, Model model){
        String status;
        try {
            Lessons newLesson;
            if(lesson==-1){
                newLesson = new Lessons(title);
                status = "added";
            }else{
                newLesson = new Lessons(lesson, title);
                status = "edited";
            }
            lessonsRepository.save(newLesson);
        }catch (Exception e){
            System.out.println(e.getMessage());
            status = "failure";
        }
        return "redirect:/lesson?status="+status;
    }
    @PostMapping("/lesson/remove")
    public String removeLesson(@RequestParam int lesson){
        String status = "";
        try {
            if(lesson==-1){
                throw new Exception("nice try");
            }
            Lessons targetLesson = lessonsRepository.findById(lesson).orElseThrow();
            lessonsRepository.delete(targetLesson);
            status="removed";
        }catch (Exception e){
            System.out.println(e.getMessage());
            status = "failure";
        }
        return "redirect:/student?status="+status;
    }
}
