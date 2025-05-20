package com.example.backend.service;

import com.example.backend.BackendApplication;
import com.example.backend.Config;
import com.example.backend.dto.PaymentProjectionDTO;
import com.example.backend.dto.SePayTransactionDTO;
import com.example.backend.model.*;
import com.example.backend.model.enums.NotificationType;
import com.example.backend.model.enums.VehicleType;
import com.example.backend.repository.*;
import com.example.backend.util.ExcelExporter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private FeedbackRepository feedbackRepository;
    @Autowired
    private NotificationItemRepository notificationRepository;
    @Autowired
    private ExcelExporter excelExporter;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private NoticementRepository noticementRepository;


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

    @GetMapping(value = "/getbillpaymentlink", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getBillPaymentLink(@RequestParam int paymentId) {
        Payment payment = paymentRepository.findById(paymentId).get();
        Apartment apartment = payment.getApartment();
        Bill bill = payment.getBill();
        Double cost;
        if (bill.getAmount() != null) {
            cost = bill.getAmount();
        }
        else {
            cost = apartment.getMonthlyFee();
            cost = cost + vehicleRepository.countVehiclesBeforeDateWithType(apartment.getApartmentId(), VehicleType.Car, LocalDateTime.now()) * 1200000.0;
            cost = cost + vehicleRepository.countVehiclesBeforeDateWithType(apartment.getApartmentId(), VehicleType.Motorbike, LocalDateTime.now()) * 70000.0;
        }
        if (LocalDateTime.now().isAfter(bill.getDueDate())) {
            cost = cost + bill.getLateFee();
        }
//        return getBaseUrl() + "/payment/pay?paymentId=" + paymentId + "&amount=" + cost;  // ví dụ trả về đường dẫn thanh toán
        byte[] qrImage = restTemplate.getForObject(
                "https://qr.sepay.vn/img?acc=VQRQACNHR7348&bank=MBBank&amount={cost}&des=PAYFOR{paymentId}",
                byte[].class,
                cost,
                paymentId
        );
        return qrImage;
    }

    @GetMapping("/pay")
    public String pay(@RequestParam Integer paymentId, @RequestParam String amount) {
        Payment payment = paymentRepository.findById(paymentId).get();
        payment.setPayAmount(new BigDecimal(amount));
        payment.setPayTime(LocalDateTime.now());
        payment = paymentRepository.save(payment);
        Feedback feedback = new Feedback(2, "Xác nhận thanh toán", "Phòng " + payment.getApartment().getApartmentName() + " đã thanh toán thành công khoản thu " + payment.getBill().getContent());
        feedback.setType(NotificationType.Success.toString());
        feedbackRepository.save(feedback);
        NotificationItem noti = new NotificationItem("Thanh toán thành công", "Phòng " + payment.getApartment().getApartmentName() + " đã thanh toán thành công khoản thu " + payment.getBill().getContent(), NotificationType.Success.toString());
        noti = notificationRepository.save(noti);
        List<Resident> res = residentRepository.findResidentsByFilters(payment.getApartment().getApartmentName(), null, "");
        NotificationItem finalNoti = noti;
        List<Noticement> ds = res.stream().map(r -> new Noticement(null, finalNoti.getId(), r.getResidentId(), false)).collect(Collectors.toList());
        noticementRepository.saveAll(ds);
        return "Thanh toán thành công";
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody SePayTransactionDTO transaction) {
        // Kiểm tra nội dung
        System.out.println("Received webhook from SePay:");
        System.out.println("Gateway: " + transaction.getGateway());
        System.out.println("Amount: " + transaction.getTransferAmount());
        System.out.println("Content: " + transaction.getContent());
        int paymentId = 0;
        int pos = transaction.getDescription().indexOf("PAYFOR");
        for (int i = pos + 6; i < transaction.getDescription().length(); i++) {
            if (transaction.getDescription().charAt(i) >= '0' && transaction.getDescription().charAt(i) <= '9') {
                paymentId = paymentId * 10 + transaction.getDescription().charAt(i) - '0';
            }
            else {
                break;
            }
        }
        pay(paymentId, transaction.getTransferAmount().toString());

        return ResponseEntity.ok("Webhook received successfully.");
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
