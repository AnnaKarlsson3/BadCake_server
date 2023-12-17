package com.dkserver.danielServer.controllers;

import com.dkserver.danielServer.models.LinkEntity;
import com.dkserver.danielServer.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dkserver.danielServer.utils.Constants.*;

@RestController
@CrossOrigin(origins = CORS_URL)
@RequestMapping(REST)
public class LinkController {

    @Autowired
    LinkService linkService;

    @PostMapping("/link")
    public ResponseEntity saveLinkLibraryData(@RequestBody LinkEntity linkEntity){
        LinkEntity linkEntityData = linkService.saveLinkLibraryDataToDb(linkEntity);
        if(linkEntityData == null){
            return new ResponseEntity(ERROR_RESPONSE_SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(linkEntityData, HttpStatus.CREATED);
    }

    @GetMapping("/link")
    public ResponseEntity getAllLinkLibraryData(){
        List<LinkEntity> linkEntityData = linkService.getAllLinkLibraryDataFromDb();
        if(linkEntityData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(LINK_RESPONSE_NO_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(linkEntityData);
    }

    @DeleteMapping("/link/{id}")
    public ResponseEntity<String> deletelinkLibraryDataById(@PathVariable Integer id){
        String response = linkService.deleteLinkLibraryPostById(id);
        if(response == null) {
            return new ResponseEntity<>(LINK_RESPONSE_NO_FOUND_ID, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
