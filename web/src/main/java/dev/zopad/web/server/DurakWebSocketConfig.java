package dev.zopad.web.server;

import dev.zopad.durak.state.Card;
import dev.zopad.durak.state.Color;
import dev.zopad.durak.state.Rank;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class DurakWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/durak");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sendCards");
        registry.addEndpoint("/sendCards").withSockJS(); // fallback
    }

    @MessageMapping("/durak")
    @SendTo("/topic/sentCards")
    public SendCardsMessage send(Message message) {
        return new SendCardsMessage("sanyi", List.of(new Card(Color.ACORN, Rank.ACE)));
    }
}
