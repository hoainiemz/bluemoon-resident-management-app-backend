package com.example.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "scheduler")
public class Scheduler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduler_id")
    private Long schedulerId;

    @Column(name = "scheduler_type", nullable = false)
    private String schedulerType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", nullable = false)
    private String content; // Store JSON as a String in a jsonb column

    @Column(name = "next_execution", nullable = false)
    private LocalDateTime nextExecution;

    @Column(name = "cycle", nullable = false)
    private String cycle; // Example values: "hour", "day", etc.

    // Getters and Setters

    public Long getSchedulerId() { return schedulerId; }
    public void setSchedulerId(Long schedulerId) { this.schedulerId = schedulerId; }

    public String getSchedulerType() { return schedulerType; }
    public void setSchedulerType(String schedulerType) { this.schedulerType = schedulerType; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getNextExecution() { return nextExecution; }
    public void setNextExecution(LocalDateTime nextExecution) { this.nextExecution = nextExecution; }

    public String getCycle() { return cycle; }
    public void setCycle(String cycle) { this.cycle = cycle; }
}
