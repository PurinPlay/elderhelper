package ru.vk.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.AttendanceRepository;
import ru.vk.bot.repository.StudentsRepository;
import ru.vk.bot.repository.templates.DateSchedule;
import ru.vk.bot.services.AttendanceService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentsController {
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private AttendanceService attendanceService;
    @GetMapping("/student")
    public String student(Model model){
        Iterable<Students> students = studentsRepository.findAll();
        model.addAttribute("students",students);
        return "student";
    }
    @PostMapping("/student/edit")
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
            updateFixAttendance(newStudent.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
            status = "failure";
        }
        return "redirect:/student?status="+status;
    }
    @PostMapping("/student/remove")
    public String removeStudent(@RequestParam int student){
        String status = "";
        try {
            if(student==-1){
                throw new Exception("nice try");
            }
            Students targetStudent = studentsRepository.findById(student).orElseThrow();
            studentsRepository.delete(targetStudent);
            status="removed";
        }catch (Exception e){
            System.out.println(e.getMessage());
            status = "failure";
        }
        return "redirect:/student?status="+status;
    }
    public void updateFixAttendance(int studentId){
        List<DateSchedule> data = attendanceRepository.findDistinctLessonDatesAndScheduleIds();
        List<Attendance> attendanceList = new ArrayList<>();
        for (var dateSchedule: data) {
            Attendance attendance = attendanceService.createEntry(studentId, dateSchedule.getScheduleId(), dateSchedule.getDate(), false);
            attendanceList.add(attendance);
        }
        attendanceRepository.saveAll(attendanceList);
    }
}
