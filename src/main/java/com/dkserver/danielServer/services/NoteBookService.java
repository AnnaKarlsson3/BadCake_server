package com.dkserver.danielServer.services;


import com.dkserver.danielServer.models.ShortNote;
import com.dkserver.danielServer.models.Link;
import com.dkserver.danielServer.models.UserEntity;
import com.dkserver.danielServer.repository.ShortnoteRepo;
import com.dkserver.danielServer.repository.LinkRepo;
import com.dkserver.danielServer.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteBookService {

    //TODO: set string to constants.class

    @Autowired
    ShortnoteRepo shortnoteRepo;
    @Autowired
    LinkRepo linkRepo;
    @Autowired
    UserRepo userRepo;

    public ShortNote saveShortNoteDataToDb(ShortNote shortNote) {
        return shortnoteRepo.save(shortNote);
    }

    public List<ShortNote> getAllShortNotesDataFromDb() {
        return shortnoteRepo.findAll();
    }

    public String deleteShortNotePostById(int id){
        Optional<ShortNote> shortNotePost = shortnoteRepo.findById(id);
        if(shortNotePost == null){
            return null;
        }
        shortnoteRepo.deleteById(id);
        return "Shortnote post deleted successful!";
    }

    public Link saveLinkLibraryDataToDb(Link link) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        link.setUserId(user.get().getId());
        return linkRepo.save(link);
    }

    public List<Link> getAllLinkLibraryDataFromDb() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        return linkRepo.findAllByUserId(user.get().getId());
    }

    public String deleteLinkLibraryPostById (int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        Link linkPost = linkRepo.findByIdAndUserId(id, user.get().getId());

        if(linkPost == null){
            return null;
        }
        linkRepo.deleteByIdAndUserId(id, user.get().getId());
        return "Linkpost deleted successful!";
    }


}
