package ru.vk.bot.VkBot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.repository.AttendanceRepository;

import java.util.List;

@RestController
public class BotController {
    @Autowired
    AttendanceRepository attendanceRepository;


    public synchronized int insertData(List<Attendance> list){
        try {
            attendanceRepository.saveAll(list);
        }catch (Exception e){
            return -1;
        }
        return 0;
    }
}
