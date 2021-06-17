package com.github.microservice.auth.server.core.listen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccessTokenMessageListener extends MessageListenerAdapter {


    private final static String AccessName = "access:";

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody());
        String patternText = new String(pattern);
        if (key.indexOf(AccessName) != 0) {
            return;
        }
        key = key.substring(AccessName.length());
        log.info("AccessToken Remove {} -> {}", patternText, key);


    }
}
