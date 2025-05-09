package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "noticement")
public class Noticement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticement_id")
    private Integer noticementId;

    @Column(name = "notification_id")
    private Integer notificationId;

    @Column(name = "resident_id")
    private Integer residentId;

    @Column(name = "watched")
    private Boolean watched;

    public Noticement(Integer noticementId, Integer notificationId, Integer residentId, Boolean watched) {
        this.noticementId = noticementId;
        this.notificationId = notificationId;
        this.residentId = residentId;
        this.watched = watched;
    }

    public Noticement () {

    }

    public Integer getNoticementId() {
        return noticementId;
    }

    public void setNoticementId(Integer noticementId) {
        this.noticementId = noticementId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
    }

    public Boolean getWatched() {
        return watched;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }
}
