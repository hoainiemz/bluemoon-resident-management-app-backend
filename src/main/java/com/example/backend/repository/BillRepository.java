package com.example.backend.repository;

import com.example.backend.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BillRepository  extends JpaRepository<Bill, Integer> {
    Optional<Bill> findBillByBillId(Integer billId);
    @Query("SELECT b FROM Bill b " +
            "WHERE (:requireFilter = 0 OR (:requireFilter > 0 AND b.required = true) OR (:requireFilter < 0 AND b.required = false)) " +
            "AND (:dueFilter = 0 OR (:dueFilter > 0 AND b.dueDate > CURRENT_TIMESTAMP) OR (:dueFilter < 0 AND b.dueDate < CURRENT_TIMESTAMP)) " +
            "AND (:searchFilter IS NULL OR b.content LIKE %:searchFilter%)")
    List<Bill> findBillsWithFilters(
            @Param("requireFilter") int requireFilter,
            @Param("dueFilter") int dueFilter,
            @Param("searchFilter") String searchFilter);
    void deleteByBillId(Integer billId);
}
