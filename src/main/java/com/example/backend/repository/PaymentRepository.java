package com.example.backend.repository;

import com.example.backend.dto.PaymentProjectionDTO;
import com.example.backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

@Query("""
    SELECT new com.example.backend.dto.PaymentProjectionDTO(
        p.paymentId,
        b.billId,
        b.dueDate,
        b.required,
        b.content,
        b.amount,
        p.payTime,
        a.apartmentName
    )
    FROM Payment p
    JOIN p.bill b
    JOIN p.apartment a
    JOIN Settlement s ON s.apartmentId = a.apartmentId
    WHERE s.residentId = :residentId
        AND (:stateFilter = 0 OR (:stateFilter = 1 AND p.payTime IS NOT NULL) OR (:stateFilter = -1 AND p.payTime IS NULL))
        AND (:requireFilter = 0 OR (:requireFilter = 1 AND b.required = true) OR (:requireFilter = -1 AND b.required = false))
        AND (:dueFilter = 0 OR (:dueFilter = 1 AND b.dueDate < CURRENT_TIMESTAMP) OR (:dueFilter = -1 AND b.dueDate >= CURRENT_TIMESTAMP))
        AND (:searchFilter IS NULL 
             OR b.content ILIKE CONCAT('%', :searchFilter, '%') 
             OR b.description LIKE CONCAT('%', :searchFilter, '%'))
""")
List<PaymentProjectionDTO> findPaymentsByResidentAndFilters(
            @Param("residentId") Integer residentId,
            @Param("stateFilter") int stateFilter,
            @Param("requireFilter") int requireFilter,
            @Param("dueFilter") int dueFilter,
            @Param("searchFilter") String searchFilter
            );



    @Query("SELECT p FROM Payment p WHERE p.bill.billId = :billId")
    List<Payment> findPaymentByBillId(@Param("billId") Integer billId);



    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.paymentId IN :ids")
    void deletePaymentsByPaymentId(@Param("ids") List<Integer> ids);

    Payment findPaymentByPaymentId(Integer paymentId);

    @Query("""
    SELECT COUNT(p) FROM Payment p
    WHERE p.payTime IS NULL
      AND p.bill.dueDate < CURRENT_TIMESTAMP
      AND p.bill.required = TRUE
      AND p.apartment.apartmentId IN (
          SELECT s.apartmentId FROM Settlement s
          WHERE s.residentId = :residentId
      )
""")
    int countUnpaidOverdueRequiredPayments(@Param("residentId") Integer residentId);

    @Query("""
    SELECT COUNT(p) FROM Payment p
    WHERE p.payTime IS NULL
      AND p.bill.dueDate >= CURRENT_TIMESTAMP
      AND p.bill.required = TRUE
      AND p.apartment.apartmentId IN (
          SELECT s.apartmentId FROM Settlement s
          WHERE s.residentId = :residentId
      )
""")
    int countUnpaidUnderdueRequiredPayments(@Param("residentId") Integer residentId);

    @Query("SELECT p FROM Payment p WHERE p.bill.billId IN :billIds")
    List<Payment> findByBillIds(@Param("billIds") List<Integer> billIds);
}
