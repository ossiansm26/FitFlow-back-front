package com.ossian.FitFlow.repository;

import com.ossian.FitFlow.model.Chat;
import com.ossian.FitFlow.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c JOIN FETCH c.participants p WHERE :sender MEMBER OF c.participants AND :recipient MEMBER OF c.participants")
    List<Chat> findChatByParticipants(@Param("sender") User sender, @Param("recipient") User recipient, Pageable pageable);



}
