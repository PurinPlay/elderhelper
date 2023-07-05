package ru.vk.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vk.bot.embeddables.AttendanceIdentity;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.models.Schedule;
import ru.vk.bot.models.Students;
import ru.vk.bot.repository.ScheduleRepository;
import ru.vk.bot.repository.StudentsRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    StudentsRepository studentsRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    public Attendance createEntry(int studentId, int scheduleId, Date date, boolean isVisited){
        Attendance out;
        Students targetStudent = studentsRepository.findById(studentId).orElseThrow();
        Schedule targetSchedule = scheduleRepository.findById(scheduleId).orElseThrow();
        AttendanceIdentity key = new AttendanceIdentity(studentId, scheduleId, date);
        out = new Attendance(key, targetSchedule, targetStudent, isVisited);
        return out;
    }
    public List<Attendance> createAttendance(int schedule_id, Date date){
        List<Students> students = studentsRepository.findAll();
        List<Attendance> out = new ArrayList<>();
        for (var student:students) {
            out.add(createEntry(student.getId(), schedule_id, date, false));
        }
        return out;
    }
}
