package com.example.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(name = "resident_id", nullable = false)
    private Integer residentId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * Mapped to VARCHAR(20) NOT NULL DEFAULT 'Info'
     * Default value in Java model is 'Info' as well.
     */
    @Column(name = "type", length = 20, nullable = false)
    private String type = "Info";

    @Column(nullable = false)
    private Boolean watched = false;

    /**
     * Mapped to TIMESTAMP DEFAULT CURRENT_TIMESTAMP
     * insertable=false/updatable=false để sử dụng giá trị do DB sinh tự động
     */
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Feedback() {
        // type và watched đã có giá trị mặc định ở field declaration
    }

    public Feedback(Integer residentId, String title, String content) {
        this.residentId = residentId;
        this.title      = title;
        this.content    = content;
    }

    public Feedback(Integer residentId,
                    String title,
                    String content,
                    String type,
                    Boolean watched) {
        this.residentId = residentId;
        this.title      = title;
        this.content    = content;
        this.type       = type;
        this.watched    = watched;
    }

    // Getters & Setters

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getWatched() {
        return watched;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", residentId=" + residentId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", watched=" + watched +
                ", createdAt=" + createdAt +
                '}';
    }
}
