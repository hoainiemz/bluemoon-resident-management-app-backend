package com.example.backend.service;

import com.example.backend.model.Noticement;
import com.example.backend.repository.NoticementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Transactional
@RestController
@RequestMapping("/noticement")
public class NoticementService {
    @Autowired
    private NoticementRepository noticementRepository;

    @GetMapping("findallbynotificationid")
    public List<Noticement> findAllByNotificationId(@RequestParam Integer id) {
        return noticementRepository.findAllByNotificationId(id);
    }

    @PostMapping("/markaswatched")
    public void markAsWatched(@RequestParam Integer notificationId, @RequestParam Integer residentId) {
        noticementRepository.markAsWatched(notificationId, residentId);
    }

    @PostMapping("/saveall")
    public void saveAll(@RequestBody List<Noticement> noticements) {
        noticementRepository.saveAll(noticements);
    }

    @PostMapping("/deletenoticementsbynoticementid")
    public void deleteNoticementsByNoticementId(@RequestBody List<Integer> dsout) {
        noticementRepository.deleteNoticementsByNoticementId(dsout);
    }
}
