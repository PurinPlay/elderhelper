package ru.vk.bot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class NavController {
    @GetMapping("/")
    public String index(Model model){
        return "index";
    }
    @GetMapping("/lesson")
    public String lesson(Model model){
        return "lesson";
    }
    @GetMapping("/edit")
    public String edit(Model model){
        return "edit";
    }
    @GetMapping("/attendance")
    public String attendance(Model model){
        return "attendance";
    }
    @GetMapping("/schedule")
    public String schedule(Model model){
        return "schedule";
    }
    @GetMapping("/student")
    public String student(Model model){
        return "student";
    }
}
