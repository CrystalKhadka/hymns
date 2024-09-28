package com.hymns.hymns.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Configures the message broker with two destinations: /app and /topic
        config.enableSimpleBroker("/topic", "/queue");  // For broadcasting topics and user-specific queues
        config.setApplicationDestinationPrefixes("/app");  // All application-bound messages will have /app prefix
        config.setUserDestinationPrefix("/user");  // Prefix for user-specific destinations like /user/{username}/queue
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register STOMP endpoint at /ws and enable SockJS fallback option
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000").withSockJS();
    }
}
