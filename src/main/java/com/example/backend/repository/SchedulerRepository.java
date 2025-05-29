package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.backend.model.Scheduler;

import java.util.List;

@Repository
public interface SchedulerRepository extends JpaRepository<Scheduler, Long> {

    // You can define custom query methods here if needed
    // Example: List<Scheduler> findBySchedulerType(String schedulerType);

    @Query(value = """
    SELECT *
    FROM scheduler
    WHERE
        scheduler_type = 'Bill'
    AND
        (:requireFilter = 0
         OR (:requireFilter = 1 AND content::text LIKE '%"required": true%')
         OR (:requireFilter = -1 AND content::text LIKE '%"required": false%'))
    AND
        (:searchFilter IS NULL
         OR :searchFilter = ''
         OR content::text ILIKE CONCAT('%', :searchFilter, '%'))
    """, nativeQuery = true)
    List<Scheduler> findBillWithFilters(
            @Param("requireFilter") int requireFilter,
            @Param("searchFilter") String searchFilter
    );


    @Query(value = """
SELECT *
FROM scheduler
WHERE
    scheduler_type = 'Notification'
AND
  (:typeFilter IS NULL OR :typeFilter = '' OR :typeFilter = 'All'
   OR content::text LIKE CONCAT('%"type": "', :typeFilter, '"%'))
AND
  (:searchFilter IS NULL OR :searchFilter = ''
   OR content::text ILIKE CONCAT('%', :searchFilter, '%'))
""", nativeQuery = true)
    List<Scheduler> findNotiWithFilters(
            @Param("typeFilter") String typeFilter,
            @Param("searchFilter") String searchFilter
    );


    Scheduler findBySchedulerId(Long schedulerId);
}
