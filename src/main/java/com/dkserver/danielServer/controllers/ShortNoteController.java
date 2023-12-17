package com.dkserver.danielServer.controllers;


import com.dkserver.danielServer.models.ShortNote;
import com.dkserver.danielServer.services.ShortNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dkserver.danielServer.utils.Constants.*;

@RestController
@CrossOrigin(origins = CORS_URL)
@RequestMapping(REST)
public class ShortNoteController {

    @Autowired
    ShortNoteService shortNoteService;

    @PostMapping("/shortnote")
    public ResponseEntity saveShortNoteData(@RequestBody ShortNote shortNote){
        ShortNote shortNotesData = shortNoteService.saveShortNoteDataToDb(shortNote);
        if(shortNotesData == null){
            return new ResponseEntity(ERROR_RESPONSE_SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(shortNotesData, HttpStatus.CREATED);
    }

    @GetMapping("/shortnote")
    public ResponseEntity getAllShortNotesData(){
        List<ShortNote> shortNotesData = shortNoteService.getAllShortNotesDataFromDb();
        if(shortNotesData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SHORTNOTE_RESPONSE_NO_POST_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shortNotesData);
    }

    @DeleteMapping("/shortnote/{id}")
    public ResponseEntity<String> deleteShortNoteDataById(@PathVariable Integer id){
            String response = shortNoteService.deleteShortNotePostById(id);
            if(response == null) {
                return new ResponseEntity<>(SHORTNOTE_RESPONSE_NO_POST_FOUND_ID, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
