package com.example.backend.repository;

import com.example.backend.model.Noticement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoticementRepository  extends JpaRepository<Noticement, Integer> {
    List<Noticement> findAllByNotificationId(Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Noticement n WHERE n.noticementId IN :ids")
    void deleteNoticementsByNoticementId(@org.springframework.data.repository.query.Param("ids") List<Integer> ids);

    @Modifying
    @Transactional
    @Query("UPDATE Noticement n SET n.watched = TRUE WHERE n.notificationId = :notificationId AND n.residentId = :residentId")
    int markAsWatched(Integer notificationId, Integer residentId);
}
