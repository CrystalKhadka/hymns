package com.hymns.hymns.controller;

import com.hymns.hymns.entity.Rental;
import com.hymns.hymns.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.*;
import java.util.List;

@Controller
@RequiredArgsConstructor

public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RentalService rentalService;


    // Endpoint to trigger a sample notification
    @GetMapping("/send-notification")
    public void sendNotification() {
        System.out.println("Sending notification...");
        String notification = "New notification from the server!";
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    @Scheduled(fixedRate = 300) // Run every 5 minutes (300,000 milliseconds)
    // Endpoint to trigger a sample notification
    public void checkRentalTimers() {
        System.out.println("Checking rental timers...");
        List<Rental> activeRentals = rentalService.getActiveRentals();
        LocalDateTime now = LocalDateTime.now();

        for (Rental rental : activeRentals) {
            // Assuming getReturnDate() returns an Instant (adjust based on actual type)
            Instant returnDate = rental.getReturnDate().toInstant();

            // Convert Instant to ZonedDateTime with system's default time zone or a specified one
            ZonedDateTime returnDateTime = returnDate.atZone(ZoneId.systemDefault());

            // Alternatively, if you need LocalDateTime, you can extract it from ZonedDateTime
            LocalDateTime localReturnDateTime = returnDateTime.toLocalDateTime();

            Duration timeLeft = Duration.between(now, localReturnDateTime);
            long hoursLeft = timeLeft.toHours();

            if (hoursLeft < 22 && hoursLeft >= 0) {
                String notification = String.format(
                        "Rental for %s is due in less than %d hours! ",
                        rental.getInstrument().getInstrumentName(),
                        hoursLeft
                );
                String user = rental.getUser().getUsername();

                System.out.println("Sending notification: " + notification + " to " + user);
//                the socket recieve url with username as the first parameter is /user/{username}/queue/notifications
                messagingTemplate.convertAndSendToUser(
                        rental.getUser().getUsername(),
                        "/queue/notifications",
                        notification
                );
            }
        }
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
