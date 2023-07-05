package ru.vk.bot.VkBot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vk.bot.Utility;
import ru.vk.bot.models.Attendance;
import ru.vk.bot.models.Schedule;
import ru.vk.bot.repository.AttendanceRepository;
import ru.vk.bot.repository.ScheduleRepository;
import ru.vk.bot.repository.StudentsRepository;
import ru.vk.bot.services.AttendanceService;
import ru.vk.bot.services.StudentService;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@EnableScheduling
@EnableAsync
public class BotService {

    private boolean scanning = false;
    List<Attendance> toSend;
    @Autowired
    private VkApiClient vk;
    @Autowired
    private GroupActor actor;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentsRepository studentsRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    AttendanceService attendanceService;
    private Random random = new Random();
    private int ts=0;

    public BotService(){

    }

    public Integer getTs() throws ClientException, ApiException {
        return vk.messages().getLongPollServer(actor).execute().getTs();
    }
    @Scheduled(fixedRate = 500)
    @Async
    public void messageChecker(){
        try{
            if (ts == 0){
                ts = getTs();
            }
            MessagesGetLongPollHistoryQuery historyQuery = vk.messages().getLongPollHistory(actor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if(!messages.isEmpty()) {
                for (Message message:messages) {
                    if(scanning){
                        attendanceEditor(message);
                    }else{
                        commandParser(message);
                    }

                }
            }
            ts = getTs();
        }catch (ClientException | ApiException e){
            System.out.println(e.getMessage());
        }
    }
    public void sendMessage(Message message, String text) throws ClientException, ApiException {
        vk.messages().send(actor).message(text).userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
    }
    public void sendMessage(Message message, String text, Keyboard keyboard) throws ClientException, ApiException {
        vk.messages().send(actor).message(text).keyboard(keyboard).userId(message.getFromId()).randomId(random.nextInt(10000)).execute();
    }

    public boolean commandParser(Message message) throws ClientException, ApiException {
        String[]commands = message.getText().split(Pattern.quote(" "));
        switch (commands[0]){
            case "Расписание":
                showSchedule(message);
                break;
            case "Отметить":
                initiateMarking(message, commands);
                break;
            case "Подтвердить":
                sendMessage(message, "Сначала нужно выбрать день и предмет с помощью `Отметить`");
                break;
            case "Очистить":
                startKeyBoard(message);
                break;
            case "Помощь":
                help(message);
                break;
            default:
                sendMessage(message, "Команда не обнаружена, используйте `Помощь` что бы посмотреть список команд.");
        }
        return true;
    }
    public boolean attendanceEditor(Message message) throws ClientException, ApiException {
        String[]commands = message.getText().split(Pattern.quote(" "));
        if (commands[0].equals("Подтвердить")){
            attendanceRepository.saveAll(toSend);
            startKeyBoard(message);
            return false;
        }
        try{
            int id = Integer.parseInt(commands[0]);
            if(studentsRepository.existsById(id)){
                for (var mark:toSend) {
                    if(mark.getStudent().getId() == id){
                        String text = (!mark.isVisited())?(mark.getStudent().getFullName()+" теперь присутствует на паре"):(mark.getStudent().getFullName()+"теперь отсутствует на паре");
                        mark.setVisited(!mark.isVisited());
                        sendMessage(message, text);
                        break;
                    }
                }
            }else {
                throw new Exception("student does not exists");
            }
        }catch (Exception e){
            sendMessage(message, "Такого студента нет");
        }
        return true;
    }
    private void help(Message message) throws ClientException, ApiException {
        sendMessage(message,
                "`Расписание` что бы посмотреть доступные индентефикаторы для расписания\n" +
                        "`Отметить {id расписаня} {дата в формате DD.MM.YYYY}` для того что бы отметить студентов отметить студентов\n");
    }
    private void initiateMarking(Message message, String[] command) throws ClientException, ApiException{
        try{
            if(command.length == 1){
                sendMessage(message, "Синтаксис команды: Отметить {id расписания} {Дата в формате DD.MM.YYYY}");
                return;
            }
            if(command.length != 3){
                throw new Exception("-1");
            }

            int schedule = Integer.parseInt(command[1]);
            Date date = Utility.parseDate(command[2]);
            List<List<KeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Collections.singletonList(new KeyboardButton().setAction(new KeyboardButtonAction().setType(TemplateActionTypeNames.TEXT).setLabel("Подтвердить"))));
            scanning = true;
            toSend = attendanceService.createAttendance(schedule, date);
            sendMessage(message, "Выберите студентов присутсвующих на занятии.", createStudentKeyboard());
            sendMessage(message, "Нажмите `Подтвердить` что бы отправить данные", new Keyboard().setButtons(buttons));
        }catch (Exception e){
            System.out.println(e.getMessage());
            sendMessage(message, "Ошибка синтаксиса");
        }
    }
    private Keyboard createStudentKeyboard(){
        Keyboard out = new Keyboard();
        List<List<KeyboardButton>> buttons = new ArrayList<>();
        for (var student:studentService.findAllSortedByFullName()) {
            KeyboardButton button = new KeyboardButton();
            button.setAction(new KeyboardButtonAction()
                    .setType(TemplateActionTypeNames.TEXT)
                    .setLabel(String.join(" ", String.valueOf(student.getId()), student.getFullName())));
            buttons.add(Collections.singletonList(button));
        }
        out.setButtons(buttons);
        out.setInline(true);
        return out;
    }

    private void showSchedule(Message message) throws ClientException, ApiException{
        String out="";
        List<Schedule> schedule = scheduleRepository.findAll();
        for (var sch:schedule) {
            out += String.join(" ", String.valueOf(sch.getId()), sch.getLesson().getTitle(), sch.getTime().toString(), "\n");
        }
        sendMessage(message, out);
    }
    private void startKeyBoard(Message message) throws ClientException, ApiException{
        Keyboard out = new Keyboard();
        List<List<KeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(new KeyboardButton().setAction(new KeyboardButtonAction().setType(TemplateActionTypeNames.TEXT).setLabel("Отметить"))));
        buttons.add(Collections.singletonList(new KeyboardButton().setAction(new KeyboardButtonAction().setType(TemplateActionTypeNames.TEXT).setLabel("Расписание"))));
        buttons.add(Collections.singletonList(new KeyboardButton().setAction(new KeyboardButtonAction().setType(TemplateActionTypeNames.TEXT).setLabel("Помощь"))));
        out.setButtons(buttons);
        sendMessage(message, "Готово", out);
    }

}
