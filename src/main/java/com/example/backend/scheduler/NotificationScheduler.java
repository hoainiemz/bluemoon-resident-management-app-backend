package com.example.backend.scheduler;

import com.example.backend.dto.NotificationSchedulerDTO;
import com.example.backend.model.Noticement;
import com.example.backend.model.NotificationItem;
import com.example.backend.model.Scheduler;
import com.example.backend.service.NoticementService;
import com.example.backend.service.NotificationService;
import com.example.backend.service.SchedulerService;
import com.example.backend.util.TimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationScheduler {
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NoticementService noticementService;

    @Scheduled(fixedRate = 60000)
    public void schedule()  {
        List<Scheduler> schedulerList = schedulerService.getScheduler("Notification");
        List<NotificationItem> notiList = new ArrayList<>();
        List<Noticement> noticementList = new ArrayList<>();
        List<NotificationSchedulerDTO> dtoList = new ArrayList<>();

        String json = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        for (int i = 0; i < schedulerList.size(); i++) {
            Scheduler sc = schedulerList.get(i);
            NotificationSchedulerDTO dto = sc.notificationDTO();
            LocalDateTime nect = TimeUtil.add(sc.getNextExecution(), sc.getCycle());
            sc.setNextExecution(nect);
            dto.getNotificationItem().setCreatedAt(nect);

            try {
                json = objectMapper.writeValueAsString(dto);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            sc.setContent(json);
            notiList.add(dto.getNotificationItem());
            dtoList.add(dto);
        }

        List<NotificationItem> newnotiList = notificationService.saveAll(notiList);

        for (int i = 0; i < schedulerList.size(); i++) {
            int idx = i;
            dtoList.get(i).getResidentIds().stream().forEach(d -> {
               noticementList.add(new Noticement(null, newnotiList.get(idx).getId(), d, false));
            });
        }

        noticementService.saveAll(noticementList);
        schedulerService.saveAll(schedulerList);
    }
}
