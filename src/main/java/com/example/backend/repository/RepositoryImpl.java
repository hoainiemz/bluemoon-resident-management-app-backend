package com.example.backend.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import com.example.backend.model.enums.GenderType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositoryImpl<T>{
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<T> executeRawSql(String sqlQuery, Class<T> type) {
        try {
            Query query = entityManager.createNativeQuery(sqlQuery, type);
            return query.getResultList();
        } finally {
            entityManager.clear(); // Clear the persistence context
        }
    }


}
