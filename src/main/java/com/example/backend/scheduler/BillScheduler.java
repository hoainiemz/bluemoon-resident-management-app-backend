package com.example.backend.scheduler;

import com.example.backend.dto.BillSchedulerDTO;
import com.example.backend.model.Apartment;
import com.example.backend.model.Bill;
import com.example.backend.model.Payment;
import com.example.backend.model.Scheduler;
import com.example.backend.service.BillService;
import com.example.backend.service.PaymentService;
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
import java.util.stream.Collectors;

@Component
public class BillScheduler {
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private BillService billService;
    @Autowired
    private PaymentService paymentService;

    @Scheduled(fixedRate = 60000)
    public void schedule() {
        List<Scheduler> schedulerList = schedulerService.getScheduler("Bill");
        List<Bill> billList = new ArrayList<>();
        List<Payment> paymentList = new ArrayList<>();
        List<BillSchedulerDTO> dtoList = new ArrayList<>();

        String json = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        for (int i = 0; i < schedulerList.size(); i++) {
            Scheduler sc = schedulerList.get(i);
            BillSchedulerDTO dto = sc.billDTO();
            LocalDateTime nect = TimeUtil.add(sc.getNextExecution(), sc.getCycle());
            sc.setNextExecution(nect);
            dto.getBill().setDueDate(nect);

            try {json = objectMapper.writeValueAsString(dto);
//            System.out.println(json);
            } catch (Exception e) {
                e.printStackTrace();
            }

            sc.setContent(json);
            billList.add(dto.getBill());
            dtoList.add(dto);
        }

        List<Bill> newbillList = billService.saveAll(billList);

        for (int i = 0; i < schedulerList.size(); i++) {
            int idx = i;
            dtoList.get(i).getApartmentIds().stream().forEach(d -> {
                paymentList.add(new Payment(new Bill(newbillList.get(idx).getBillId()), new Apartment(d), null, null));
            });

        }
        paymentService.saveAllPayments(paymentList);

        schedulerService.saveAll(schedulerList);
    }
}
