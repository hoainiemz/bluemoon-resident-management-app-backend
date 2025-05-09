package com.example.backend.service;

import com.example.backend.model.Feedback;
import com.example.backend.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/feedback")
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @PostMapping("/save")
    public Feedback save(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @GetMapping("/gettopfeedbackbywatchedstatusorderbycreatedatdesc")
    public List<Feedback> getTopFeedbackByWatchedStatusOrderByCreatedAtDesc(@RequestParam boolean watchedStatus) {
        return feedbackRepository.findAllByWatchedFilter(watchedStatus, PageRequest.of(0, 20));
    }
}
