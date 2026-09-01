package com.cwr.model;

import java.time.LocalDate;
import java.util.Objects;

public record ToDo(
        String id,
        String title,
        String user,
        LocalDate dueDate,
        int priority, // 1 (High) to 5 (Low)
        boolean completed) {

}
