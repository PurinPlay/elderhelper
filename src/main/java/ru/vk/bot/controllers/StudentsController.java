package ru.vk.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vk.bot.models.Lessons;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.StudentsRepository;

@Controller
public class StudentsController {
    @Autowired
    private StudentsRepository studentsRepository;
    @GetMapping("/student")
    public String student(Model model){
        Iterable<Students> students = studentsRepository.findAll();
        model.addAttribute("students",students);
        return "student";
    }
    @PostMapping("/students/edit")
    public String editStudent(@RequestParam int student, @RequestParam String fullname ,Model model){
        String status = "";
        try {
            Students newStudent;
            if(student==-1){
                newStudent = new Students(fullname);
                status = "added";
            }else{
                newStudent = new Students(student, fullname);
                status = "edited";
            }
            studentsRepository.save(newStudent);
        }catch (Exception e){
            status = "failure";
        }
        return "redirect:/student?status="+status;
    }

}