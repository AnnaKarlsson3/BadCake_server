package com.dkserver.danielServer.services;

import com.dkserver.danielServer.models.LinkEntity;
import com.dkserver.danielServer.repository.LinkRepo;
import com.dkserver.danielServer.utils.GetLoggedInUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dkserver.danielServer.utils.Constants.LINK_RESPONSE_DELETE_OK;

@Transactional
@Service
public class LinkService {

    @Autowired
    GetLoggedInUser getLoggedInUser;

    @Autowired
    LinkRepo linkRepo;

    public LinkEntity saveLinkLibraryDataToDb(LinkEntity linkEntity) {
        linkEntity.setUserId(getLoggedInUser.getLoggedInUserId());
        return linkRepo.save(linkEntity);
    }

    public List<LinkEntity> getAllLinkLibraryDataFromDb() {
        return linkRepo.findAllByUserId(getLoggedInUser.getLoggedInUserId());
    }

    public String deleteLinkLibraryPostById (int id){
        LinkEntity linkEntityPost = linkRepo.findByIdAndUserId(id, getLoggedInUser.getLoggedInUserId());
        if(linkEntityPost == null){
            return null;
        }
        linkRepo.deleteByIdAndUserId(id, getLoggedInUser.getLoggedInUserId());
        return LINK_RESPONSE_DELETE_OK;
    }
}
