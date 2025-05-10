package com.example.backend.service;

import com.example.backend.BackendApplication;
import com.example.backend.dto.PaymentProjectionDTO;
import com.example.backend.model.*;
import com.example.backend.model.enums.VehicleType;
import com.example.backend.repository.ApartmentRepository;
import com.example.backend.repository.PaymentRepository;
import com.example.backend.repository.SettlementRepository;
import com.example.backend.repository.VehicleRepository;
import com.example.backend.util.ExcelExporter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping("/payment")
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ExcelExporter excelExporter;


    @GetMapping("/findpaymentbybillid")
    public List<Payment> findPaymentByBillId(@RequestParam int billId) {
        return paymentRepository.findPaymentByBillId(billId);
    }


    @Transactional
    @PostMapping("/saveallpayments")
    public void saveAllPayments(@RequestBody List<Payment> payments) {
        for (int i = 0; i < 10; i++) {
            try {
                paymentRepository.saveAll(payments);
            }
            catch (Exception e) {
                continue;
            }
        }
    }


    @Transactional
    @DeleteMapping("/deletepayments")
    public void deletePayments(@RequestBody List<Integer> dsOut) {
        if (dsOut == null || dsOut.isEmpty()) {
            throw new IllegalArgumentException("List of bill IDs cannot be null or empty");
        }
        paymentRepository.deletePaymentsByPaymentId(dsOut);
    }

    @Transactional
    @GetMapping("/getpaymentprojectionbyresidentidandfilters")
    public List<PaymentProjectionDTO> getPaymentProjectionByResidentIdAndFilters(@RequestParam Integer residentId, @RequestParam int stateFilter, @RequestParam int requireFilter, @RequestParam int dueFilter, @RequestParam String searchFilter) {
        return paymentRepository.findPaymentsByResidentAndFilters(residentId, stateFilter, requireFilter, dueFilter, searchFilter);
    }

    @Transactional
    @PostMapping("/generatepaymentsforbill")
    public void generatePaymentsForBill(@RequestBody Bill bill) {
        List<Apartment> occupied = apartmentRepository.findOccupiedApartments();

        List<Payment> payments = occupied.stream().map(ap -> {
            Payment p = new Payment();
            p.setBill(bill);               // liên kết đến Bill
            p.setApartment(ap);            // liên kết đến Apartment
            p.setPayAmount(BigDecimal.ZERO);
            p.setPayTime(null);
            return p;
        }).collect(Collectors.toList());

        paymentRepository.saveAll(payments);
    }

    @GetMapping("/getbillpaymentlink")
    public String getBillPaymentLink(@RequestParam int paymentId) {
        Payment payment = paymentRepository.findById(paymentId).get();
        Apartment apartment = payment.getApartment();
        Bill bill = payment.getBill();
        Double cost;
        if (bill.getAmount() != null) {
            cost = bill.getAmount();
        }
        else {
            cost = apartment.getMonthlyFee();
        }
        cost = cost + vehicleRepository.countVehiclesBeforeDateWithType(apartment.getApartmentId(), VehicleType.Car, LocalDateTime.now()) * 1200000.0;
        cost = cost + vehicleRepository.countVehiclesBeforeDateWithType(apartment.getApartmentId(), VehicleType.Motorbike, LocalDateTime.now()) + 70000.0;
        if (LocalDateTime.now().isAfter(bill.getDueDate())) {
            cost = cost + bill.getLateFee();
        }
        return getBaseUrl() + "/payment/pay?paymentId=" + paymentId + "&amount=" + cost;  // ví dụ trả về đường dẫn thanh toán
    }

    @GetMapping("/pay")
    public String pay(@RequestParam Integer paymentId, @RequestParam String amount) {
        Payment payment = paymentRepository.findById(paymentId).get();
        payment.setPayAmount(new BigDecimal(amount));
        payment.setPayTime(LocalDateTime.now());
        paymentRepository.save(payment);
        return "Thanh toán thành công";
    }

    @Transactional
    @GetMapping("/getreceipt")
    public List<InvoiceItem> getReceipt(@RequestParam Integer paymentId) {
        Payment payment = paymentRepository.findPaymentByPaymentId(paymentId);
        Apartment apartment = payment.getApartment();
        Bill bill = payment.getBill();
        List<InvoiceItem> res = new java.util.ArrayList<>();
        if (bill.getAmount() != null) {
            res.add(new InvoiceItem("Số tiền", 1, bill.getAmount()));
            res.add(new InvoiceItem("Phí nộp muộn", 1, LocalDateTime.now().isAfter(bill.getDueDate()) ? bill.getLateFee() : 0));
        }
        else {
            res.add(new InvoiceItem("Tiền phòng", 1, apartment.getMonthlyRentPrice().doubleValue()));
            res.add(new InvoiceItem("Tiền điện", apartment.getLastMonthElectricIndex(), apartment.getElectricUnitPrice().doubleValue()));
            res.add(new InvoiceItem("Tiền nước", apartment.getLastMonthWaterIndex(), apartment.getWaterUnitPrice().doubleValue()));
            res.add(new InvoiceItem("Tiền gửi ô tô", vehicleRepository.countVehiclesBeforeDateWithType(apartment.getApartmentId(), VehicleType.Car, LocalDateTime.now()), 1200000.0));
            res.add(new InvoiceItem("Tiền gửi xe máy", vehicleRepository.countVehiclesBeforeDateWithType(apartment.getApartmentId(), VehicleType.Motorbike, LocalDateTime.now()), 70000.0));
            res.add(new InvoiceItem("Phí nộp muộn", 1, LocalDateTime.now().isAfter(bill.getDueDate()) ? bill.getLateFee() : 0));
        }
        return res;
    }


    private String getBaseUrl() {
        String scheme = request.getScheme(); // http hoặc https
        String serverName = request.getServerName(); // localhost, domain
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath(); // thường là ""


        boolean isDefaultPort = ("http".equals(scheme) && serverPort == 80) ||
                ("https".equals(scheme) && serverPort == 443);

        return scheme + "://" + serverName + (isDefaultPort ? "" : ":" + serverPort) + contextPath;
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@RequestParam List<Integer> billIds) {
        List<Payment> payments = paymentRepository.findByBillIds(billIds);

        Map<String, Map<String, String>> table = new LinkedHashMap<>();
        Set<String> billNames = new TreeSet<>();

        for (Payment payment : payments) {
            String apartmentName = payment.getApartment().getApartmentName();
            String billname = payment.getBill().getContent();
            BigDecimal payAmount = payment.getPayAmount();
            LocalDateTime payTime = payment.getPayTime();
            billNames.add(billname);

            table.putIfAbsent(apartmentName, new HashMap<>());

            String value = (payTime == null) ? "Chưa đóng" : payAmount.toPlainString();
            table.get(apartmentName).put(billname, value);
        }

        List<String> columns = new ArrayList<>(billNames);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            excelExporter.exportToStream(table, columns, out); // phiên bản nhận OutputStream
        }
        catch (Exception e) {
            return null;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payments.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(out.toByteArray());
    }
}
