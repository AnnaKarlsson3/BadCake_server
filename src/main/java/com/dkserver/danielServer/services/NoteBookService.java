package com.dkserver.danielServer.services;


import com.dkserver.danielServer.models.Lathund;
import com.dkserver.danielServer.models.LinkLibrary;
import com.dkserver.danielServer.models.UserEntity;
import com.dkserver.danielServer.repository.LathundRepo;
import com.dkserver.danielServer.repository.LinkLibraryRepo;
import com.dkserver.danielServer.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteBookService {

    @Autowired
    LathundRepo lathundRepo;
    @Autowired
    LinkLibraryRepo linkLibraryRepo;
    @Autowired
    UserRepo userRepo;


    public Lathund saveLathundDataToDb(Lathund lathund) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        lathund.setUserId(user.get().getId());
        return lathundRepo.save(lathund);
    }

    public List<Lathund> getAllLathundDataFromDb() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        return lathundRepo.findAllByUserId(user.get().getId());
    }

    public String deleteLathundPostById (int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        Lathund lathundPost = lathundRepo.findByIdAndUserId(id, user.get().getId());

        if(lathundPost == null){
            return null;
        }
        lathundRepo.deleteByIdAndUserId(id, user.get().getId());
        return "Lathundpost deleted successful!";
    }

    public LinkLibrary saveLinkLibraryDataToDb(LinkLibrary linkLibrary) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        linkLibrary.setUserId(user.get().getId());
        return linkLibraryRepo.save(linkLibrary);
    }

    public List<LinkLibrary> getAllLinkLibraryDataFromDb() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        return linkLibraryRepo.findAllByUserId(user.get().getId());
    }

    public String deleteLinkLibraryPostById (int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        LinkLibrary linkLibraryPost = linkLibraryRepo.findByIdAndUserId(id, user.get().getId());

        if(linkLibraryPost == null){
            return null;
        }
        linkLibraryRepo.deleteByIdAndUserId(id, user.get().getId());
        return "LinkLibrarypost deleted successful!";
    }


}
