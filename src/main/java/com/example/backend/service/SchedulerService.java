package com.example.backend.service;

import com.example.backend.model.Payment;
import com.example.backend.model.Scheduler;
import com.example.backend.repository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/scheduler")
public class SchedulerService {
    @Autowired
    private SchedulerRepository schedulerRepository;

    public SchedulerService(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @Transactional
    @PostMapping("/save")
    public void save(@RequestBody Scheduler scheduler) {
        for (int i = 0; i < 10; i++) {
            try {
                schedulerRepository.save(scheduler);
                return;
            }
            catch (Exception e) {
                continue;
            }
        }
    }

    @Transactional
    @GetMapping("/getbillbyfilter")
    public List<Scheduler> getBillByFilter(@RequestParam int requireFilter, @RequestParam(required = false, defaultValue = "") String searchFilter) {
        return schedulerRepository.findBillWithFilters(requireFilter, searchFilter);
    }

    @Transactional
    @GetMapping("/getbyid")
    public Scheduler getById(@RequestParam int id) {
        return schedulerRepository.findById(Long.valueOf(id)).get();
    }

    @Transactional
    @DeleteMapping("/delete") // Delete by id
    public void deleteById(@RequestParam int id) {
        schedulerRepository.deleteById(Long.valueOf(id));
    }

    @Transactional
    @GetMapping("/getnotificationbyfilter")
    public List<Scheduler> getNotificationByFilter(@RequestParam(required = false, defaultValue = "") String typeFilter, @RequestParam(required = false, defaultValue = "") String searchFilter) {
        return schedulerRepository.findNotiWithFilters(typeFilter, searchFilter);
    }
}
