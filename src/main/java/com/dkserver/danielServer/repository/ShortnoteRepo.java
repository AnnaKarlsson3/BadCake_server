package com.dkserver.danielServer.repository;


import com.dkserver.danielServer.models.ShortNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortnoteRepo extends JpaRepository<ShortNote, Integer> {

    @Query("SELECT s FROM ShortNote s WHERE s.userId = :userId")
    List<ShortNote> findAllByUserId(@Param("userId") String userId);

    @Query("SELECT s FROM ShortNote s WHERE s.id = :id AND s.userId = :userId")
    Optional<ShortNote> findByIdAndUserId(@Param("id") Integer id, @Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ShortNote s WHERE s.id = :id AND s.userId = :userId")
    void deleteByIdAndUserId(@Param("id") Integer id, @Param("userId") String userId);


}
