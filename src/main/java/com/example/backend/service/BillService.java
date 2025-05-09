package com.example.backend.service;

import com.example.backend.model.Bill;
import com.example.backend.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Transactional
@RestController
@RequestMapping("/bill")
public class    BillService {
    @Autowired
    BillRepository billRepository;

    @PostMapping("save")
    public Bill save(@RequestBody Bill bill) {
        return billRepository.save(bill);
    }

    @GetMapping("findbillbybillid")
    public Bill findBillByBillId(@RequestParam Integer billId) {
        return billRepository.findBillByBillId(billId).get();
    }

    @GetMapping("findbillbyfilters")
    @Transactional(readOnly = true)
    public List<Bill> findBillsByFilters(@RequestParam int requireFilter, @RequestParam int dueFilter, @RequestParam String searchFilter) {
        for (int retry = 0; retry < 3; retry++) {
            try {
                return billRepository.findBillsWithFilters(requireFilter, dueFilter, searchFilter);
            } catch (Exception e) {
                if (retry == 2) throw e;
            }
        }
        return List.of(); // Fallback empty list if all retries fail
    }


    @PostMapping("updatebill")
    @Transactional
    public void updateBill(@RequestBody Bill bill) {
        int d = 10;
        while (d --> 0) {
            try {
                billRepository.save(bill);
                return;
            } catch (Exception e) {
                continue;
            }
        }
    }

    @DeleteMapping("deletebillbyid")
    public void deleteBillById(@RequestParam Integer id) {
        billRepository.deleteByBillId(id);
    }
}
