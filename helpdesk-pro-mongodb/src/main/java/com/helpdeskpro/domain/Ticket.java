package com.helpdeskpro.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;

    private String subject;

    private String description;

    private Priority priority;

    private TicketStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime dueAt;


    private Employee submittedBy;


    private Employee assignedTo;


}
