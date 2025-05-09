package com.example.backend.service;

import com.example.backend.model.NotificationItem;
import com.example.backend.model.enums.NotificationType;
import com.example.backend.repository.NotificationItemRepository;
import com.example.backend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Transactional
@RestController
@RequestMapping("/notification")
public class NotificationService {
    @Autowired
    private NotificationItemRepository notificationItemRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/findbyid")
    public NotificationItem findById(@RequestParam Integer id) {
        return notificationItemRepository.findById(id).get();
    }

    @GetMapping("/findnotifications")
    public List<NotificationItem> findNotifications(@RequestParam String typeFilter, @RequestParam String searchFilter) {
        return notificationItemRepository.findNotifications(typeFilter, searchFilter);
    }

    @GetMapping("/findtopbyresidentidandwatchedstatusorderbycreatedatdesc")
    public List<NotificationItem> findTopByResidentIdAndWatchedStatusOrderByCreatedAtDesc(@RequestParam Integer residentId, @RequestParam Boolean unReadOnly) {
        List<NotificationItem> res = notificationItemRepository.findTopByResidentIdAndWatchedStatusOrderByCreatedAtDesc(residentId, unReadOnly, PageRequest.of(0, 20));
        int overDue = paymentRepository.countUnpaidOverdueRequiredPayments(residentId);
        int underDue = paymentRepository.countUnpaidUnderdueRequiredPayments(residentId);
        if (overDue > 0 || underDue > 0) {
            res.add(0, new NotificationItem("Nhắc nhở thu phí", "Bạn có " + overDue + " khoản thu chưa đóng đã quá hạn và " + underDue + " khoản thu chưa quá hạn.\n Hãy đóng các khoản thu trên sớm nhất có thể.", NotificationType.Warning.toString()));
        }
        return res;
    }

    @PostMapping("/save")
    public NotificationItem save(@RequestBody NotificationItem notificationItem) {
        return notificationItemRepository.save(notificationItem);
    }

    @DeleteMapping("/deletenotificationbyid")
    public void deleteNotificationById(@RequestParam Integer id) {
        notificationItemRepository.deleteByNotificationId(id);
    }

    @GetMapping("/findall")
    public List<NotificationItem> findAll() {
        return notificationItemRepository.findAll();
    }
}
