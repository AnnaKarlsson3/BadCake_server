package com.dkserver.danielServer.repository;


import com.dkserver.danielServer.models.ShortNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShortnoteRepo extends JpaRepository<ShortNote, Integer> {

    @Query(value = "SELECT * FROM shortnotes WHERE user_id = :userId", nativeQuery = true)
    List<ShortNote> findAllByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM shortnotes WHERE id = :id AND user_id = :userId", nativeQuery = true)
    ShortNote findByIdAndUserId(@Param("id") Integer id, @Param("userId") String userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM shortnotes WHERE id = :id AND user_id = :userId", nativeQuery = true)
    void deleteByIdAndUserId(@Param("id") Integer id,@Param("userId") String userId);
}
