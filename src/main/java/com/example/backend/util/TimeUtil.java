package com.example.backend.util;

import java.time.LocalDateTime;

public class TimeUtil {
    public static LocalDateTime add(LocalDateTime dateTime, String unit) {
        switch (unit) {
            case "Phút":
                return dateTime.plusMinutes(1);
            case "Giờ":
                return dateTime.plusHours(1);
            case "Ngày":
                return dateTime.plusDays(1);
            case "Tuần":
                return dateTime.plusWeeks(1);
            case "Tháng":
                return dateTime.plusMonths(1);
            case "Năm":
                return dateTime.plusYears(1);
        }
        return null;
    }
}
