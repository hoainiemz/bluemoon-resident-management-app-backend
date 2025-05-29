package com.example.backend.dto;

import com.example.backend.model.NotificationItem;

import java.util.List;

public class NotificationSchedulerDTO {
    Integer id;
    NotificationItem notificationItem;
    List<Integer> residentIds;

    public NotificationSchedulerDTO() {
    }

    public NotificationSchedulerDTO(NotificationItem notificationItem, List<Integer> residentIds) {
        this.notificationItem = notificationItem;
        this.residentIds = residentIds;
    }

    public NotificationSchedulerDTO(Integer id, NotificationItem notificationItem, List<Integer> residentIds) {
        this.id = id;
        this.notificationItem = notificationItem;
        this.residentIds = residentIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NotificationItem getNotificationItem() {
        return notificationItem;
    }

    public void setNotificationItem(NotificationItem notificationItem) {
        this.notificationItem = notificationItem;
    }

    public List<Integer> getResidentIds() {
        return residentIds;
    }

    public void setResidentIds(List<Integer> residentIds) {
        this.residentIds = residentIds;
    }
}
