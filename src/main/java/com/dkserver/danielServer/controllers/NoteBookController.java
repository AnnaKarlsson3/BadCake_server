package com.dkserver.danielServer.controllers;


import com.dkserver.danielServer.models.ShortNote;
import com.dkserver.danielServer.models.Link;
import com.dkserver.danielServer.services.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/rest")
public class NoteBookController {

    //TODO: set string to constants.class

    @Autowired
    NoteBookService noteBookService;

    @PostMapping("/shortnote")
    public ResponseEntity<String> saveShortNoteData(@RequestBody ShortNote shortNote){
        ShortNote shortNotesData = noteBookService.saveShortNoteDataToDb(shortNote);
        if(shortNotesData == null){
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("shortnote post uploaded successful!", HttpStatus.CREATED);
    }

    @GetMapping("/shortnote")
    public ResponseEntity getAllShortNotesData(){
        List<ShortNote> shortNotesData = noteBookService.getAllShortNotesDataFromDb();
        if(shortNotesData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No shortNotes posts found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(shortNotesData);
    }

    @DeleteMapping("/shortnote/{id}")
    public ResponseEntity<String> deleteShortNoteDataById(@PathVariable Integer id){
            String response = noteBookService.deleteShortNotePostById(id);
            if(response == null) {
                return new ResponseEntity<>("No shortnote posts found with that id!", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/link")
    public ResponseEntity<String> saveLinkLibraryData(@RequestBody Link link){
        Link linkData = noteBookService.saveLinkLibraryDataToDb(link);
        if(linkData == null){
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Linkpost uploaded successful!", HttpStatus.CREATED);
    }

    @GetMapping("/link")
    public ResponseEntity getAllLinkLibraryData(){
        List<Link> linkData = noteBookService.getAllLinkLibraryDataFromDb();
        if(linkData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Linkposts found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(linkData);
    }

    @DeleteMapping("/link/{id}")
    public ResponseEntity<String> deletelinkLibraryDataById(@PathVariable Integer id){
        String response = noteBookService.deleteLinkLibraryPostById(id);
        if(response == null) {
            return new ResponseEntity<>("No Linkpost found with that id!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
