package ru.vk.bot.VkBot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vk.bot.models.Attendance;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@EnableScheduling
@EnableAsync
public class BotService {
    @Autowired
    private VkApiClient vk;
    @Autowired
    private GroupActor actor;
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
                    commandParser(message);
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
    public boolean commandParser(Message message) throws ClientException, ApiException {
        String[]commands = message.getText().split(Pattern.quote(" "));
        switch (commands[0]){
            case "Расписание":
            case "Отметить":
                sendMessage(message, "TBA");
            case "Подтвердить":
                sendMessage(message, "Сначала нужно выбрать день и предмет с помощью `Отметить`");
                break;
            case "Помощь":
                help(message);
                break;
            default:
                sendMessage(message, "Команда не обнаружена, используйте `Помощь` что бы посмотреть список команд.");
        }
        return true;
    }
    private void help(Message message) throws ClientException, ApiException {
        sendMessage(message,
                "`Расписание` что бы посмотреть доступные индентефикаторы для расписания\n" +
                        "`Отметить {id расписаня} {дата в формате yyyy-dd-mm}` для того что получить опрос в котором можно отметить студентов\n" +
                        "`Подтвердить` для сохранения посещаемости");
    }
}
