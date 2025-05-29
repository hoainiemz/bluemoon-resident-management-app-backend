package com.example.backend.dto;

import com.example.backend.model.NotificationItem;

import java.util.List;

public class NotificationSchedulerDTO {
    NotificationItem notificationItem;
    List<Integer> residentIds;

    public NotificationSchedulerDTO() {
    }

    public NotificationSchedulerDTO(NotificationItem notificationItem, List<Integer> residentIds) {
        this.notificationItem = notificationItem;
        this.residentIds = residentIds;
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
