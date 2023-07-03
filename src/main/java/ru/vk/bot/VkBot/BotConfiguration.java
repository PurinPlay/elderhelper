package ru.vk.bot.VkBot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
public class BotConfiguration {
    @Value("${vk.groupId}")
    private int groupId;
    @Value("${vk.token}")
    private String token;
    @Bean
    public VkApiClient getClient(){
        return new VkApiClient(HttpTransportClient.getInstance());
    }
    @Bean
    public GroupActor getActor(){
        return new GroupActor(groupId, token);
    }

}
