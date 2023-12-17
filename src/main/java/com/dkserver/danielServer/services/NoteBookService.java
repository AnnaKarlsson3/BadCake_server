package com.dkserver.danielServer.services;


import com.dkserver.danielServer.models.Customer;
import com.dkserver.danielServer.models.ShortNote;
import com.dkserver.danielServer.models.Link;
import com.dkserver.danielServer.models.UserEntity;
import com.dkserver.danielServer.repository.CustomerRepo;
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


    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    ShortnoteRepo shortnoteRepo;
    @Autowired
    LinkRepo linkRepo;
    @Autowired
    UserRepo userRepo;

    public Customer saveCustomerDataToDb(Customer customer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        customer.setUserId(user.get().getId());
        return customerRepo.save(customer);
    }

    public List<Customer> getAllCustomerDataFromDb() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        return customerRepo.findAllByUserId(user.get().getId());
    }


    public ShortNote saveLathundDataToDb(ShortNote shortNote) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        shortNote.setUserId(user.get().getId());
        return shortnoteRepo.save(shortNote);
    }

    public List<ShortNote> getAllLathundDataFromDb() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        return shortnoteRepo.findAllByUserId(user.get().getId());
    }

    public String deleteLathundPostById (int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByUsername(authentication.getName());
        ShortNote shortNotePost = shortnoteRepo.findByIdAndUserId(id, user.get().getId());

        if(shortNotePost == null){
            return null;
        }
        shortnoteRepo.deleteByIdAndUserId(id, user.get().getId());
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
