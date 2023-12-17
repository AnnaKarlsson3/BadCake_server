package com.dkserver.danielServer.services;


import com.dkserver.danielServer.models.ShortNote;
import com.dkserver.danielServer.repository.ShortnoteRepo;
import com.dkserver.danielServer.utils.GetLoggedInUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dkserver.danielServer.utils.Constants.SHORTNOTE_RESPONSE_DELETE_OK;

@Transactional
@Service
public class ShortNoteService {

    @Autowired
    ShortnoteRepo shortnoteRepo;

    @Autowired
    GetLoggedInUser getLoggedInUser;


    public ShortNote saveShortNoteDataToDb(ShortNote shortNote) {
        shortNote.setUserId(getLoggedInUser.getLoggedInUserId());
        return shortnoteRepo.save(shortNote);
    }

    public List<ShortNote> getAllShortNotesDataFromDb() {
        return shortnoteRepo.findAllByUserId(getLoggedInUser.getLoggedInUserId());
    }

    public String deleteShortNotePostById(int id){
        String userId = getLoggedInUser.getLoggedInUserId();
        Optional<ShortNote> shortNotePost = shortnoteRepo.findByIdAndUserId(id, userId);
        if(shortNotePost == null){
            return null;
        }
        shortnoteRepo.deleteByIdAndUserId(id, userId);
        return SHORTNOTE_RESPONSE_DELETE_OK;
    }




}
