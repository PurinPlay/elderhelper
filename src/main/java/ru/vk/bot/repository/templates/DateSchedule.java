package ru.vk.bot.repository.templates;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vk.bot.models.Schedule;

import java.sql.Date;

public class DateSchedule {
    Date date;
    int scheduleId;
    public DateSchedule(){

    }

    public DateSchedule(Date date, int scheduleId) {
        this.date = date;
        this.scheduleId = scheduleId;
    }

    public Date getDate() {
        return date;
    }

    public int getScheduleId() {
        return scheduleId;
    }
}
