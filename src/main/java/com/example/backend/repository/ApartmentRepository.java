package com.example.backend.repository;

import com.example.backend.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, String> {

    boolean existsByApartmentName(String name);

    Apartment findByApartmentId(Integer apartmentId);

    @Query("""
    SELECT DISTINCT p.apartment.apartmentId
    FROM Payment p
    WHERE p.bill.billId = :billId
    """)
    List<Integer> findApartmentIdsByBillId(@Param("billId") Integer billId);

    Apartment findByApartmentName(String apartmentName);

    @Query("""
       SELECT DISTINCT a
       FROM Apartment a
       JOIN Settlement s ON s.apartmentId = a.apartmentId
    """)
    List<Apartment> findOccupiedApartments();

    void deleteByApartmentId(Integer apartmentId);
}
