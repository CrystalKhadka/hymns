package com.hymns.hymns.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Endpoint to trigger a sample notification
    @GetMapping("/send-notification")
    public void sendNotification() {
        System.out.println("Sending notification...");
        String notification = "New notification from the server!";
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    @MessageMapping("notification")
    @SendTo("/topic/notifications") // This broadcasts the message to all subscribers
    public String handleNotification(String notification) {
        // Process the notification (e.g., log it or save to a database)
        System.out.println("Received notification: " + notification);

        // You can return a response if needed
        return notification; // Optionally return the notification or a confirmation message
    }
}
