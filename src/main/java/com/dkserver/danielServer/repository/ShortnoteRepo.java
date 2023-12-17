package com.dkserver.danielServer.repository;


import com.dkserver.danielServer.models.ShortNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortnoteRepo extends JpaRepository<ShortNote, Integer> {

}
