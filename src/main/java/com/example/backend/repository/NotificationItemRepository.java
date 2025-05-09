package com.example.backend.repository;

import com.example.backend.model.NotificationItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationItemRepository extends JpaRepository<NotificationItem, Integer> {

    @Query(value = "SELECT * FROM notification " +
            "WHERE (:typeFilter = 'All' OR type = :typeFilter) " +
            "AND (:searchFilter IS NULL OR title ILIKE CONCAT('%', :searchFilter, '%') " +
            "OR message ILIKE CONCAT('%', :searchFilter, '%'))",
            nativeQuery = true)
    List<NotificationItem> findNotifications(
            @Param("typeFilter") String typeFilter,
            @Param("searchFilter") String searchFilter
    );
    // Custom query methods can be added here if neede

    @Query("SELECT n FROM NotificationItem n JOIN Noticement nm ON n.notificationId = nm.notificationId " +
            "WHERE nm.residentId = :residentId " +
            "ORDER BY n.createdAt DESC")
    List<NotificationItem> findTopByResidentIdOrderByCreatedAtDesc(@Param("residentId") Integer residentId, Pageable pageable);


    @Query("SELECT n FROM NotificationItem n JOIN Noticement nm ON n.notificationId = nm.notificationId " +
            "WHERE nm.residentId = :residentId " +
            "AND (:showUnWatchedOnly = false OR nm.watched = false) " +
            "ORDER BY n.createdAt DESC")
    List<NotificationItem> findTopByResidentIdAndWatchedStatusOrderByCreatedAtDesc(
            @Param("residentId") Integer residentId,
            @Param("showUnWatchedOnly") Boolean showUnWatchedOnly,
            Pageable pageable);
    void deleteByNotificationId(Integer notificationId);
}
