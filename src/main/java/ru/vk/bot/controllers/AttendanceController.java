package ru.vk.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.models.Lessons;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.AttendanceRepository;
import ru.vk.bot.repository.LessonsRepository;
import ru.vk.bot.repository.ScheduleRepository;
import ru.vk.bot.repository.StudentsRepository;

import java.sql.Date;
import java.util.List;

@Controller
public class AttendanceController {
    @Autowired
    LessonsRepository lessonsRepository;
    @Autowired
    StudentsRepository studentsRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    @GetMapping("/attendance")
    public String schedule(Model model, @RequestParam(defaultValue = "-1") int lesson){
        Iterable<Lessons> lessons = lessonsRepository.findAll();
        model.addAttribute("lessons", lessons);
        if (lesson == -1){
            model.addAttribute("data", -1);
        }else{
            model.addAttribute("data", buildAttendanceTable(lesson));
        }
        return "attendance";
    }
    public String[][] buildAttendanceTable(int lesson){
        String[][]out;
        List<Students> students = studentsRepository.findAll(Sort.by(Sort.Direction.ASC, "fullName"));
        out = new String[students.size()+1][];
        out[0] = buildAttendanceDateRow(lesson);
        for (int i = 1; i < out.length; i++) {
            out[i] = buildAttendanceRow(lesson, students.get(i-1).getId());
        }
        return out;
    }
    private String[] buildAttendanceDateRow(int lesson){
        List<Date> temp = attendanceRepository.findDatesByLessonId(lesson);
        String[]out = new String[temp.size()+1];
        out[0] = "";
        for (int i = 1; i < out.length; i++) {
           out[i] = temp.get(i-1).toString();
        }
        return out;
    }
    private String[] buildAttendanceRow(int lesson, int studentId){
        List<Attendance> temp = attendanceRepository.findLessonAttendanceForStudent(lesson, studentId);
        String[]out = new String[temp.size()+1];
        out[0] = studentsRepository.findById(studentId).orElseThrow().getFullName();
        for (int i = 1; i < out.length; i++) {
            out[i] = (temp.get(i-1).isVisited())?"Присутствовал":"Отсутствовал";
        }
        return out;
    }
}
