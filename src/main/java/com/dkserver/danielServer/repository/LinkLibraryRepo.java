package com.dkserver.danielServer.repository;


import com.dkserver.danielServer.models.Lathund;
import com.dkserver.danielServer.models.LinkLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LinkLibraryRepo extends JpaRepository<LinkLibrary, Integer> {

    @Query(value = "SELECT * FROM link_library WHERE user_id = :userId", nativeQuery = true)
    List<LinkLibrary> findAllByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM link_library WHERE id = :id AND user_id = :userId", nativeQuery = true)
    LinkLibrary findByIdAndUserId(@Param("id") Integer id,@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM link_library WHERE id = :id AND user_id = :userId", nativeQuery = true)
    void deleteByIdAndUserId(@Param("id") Integer id,@Param("userId") Integer userId);
}
