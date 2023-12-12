package com.dkserver.danielServer.controllers;


import com.dkserver.danielServer.models.Lathund;
import com.dkserver.danielServer.models.LinkLibrary;
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

    @Autowired
    NoteBookService noteBookService;

    @PostMapping("/lathund")
    public ResponseEntity<String> saveLathundData(@RequestBody Lathund lathund){
        Lathund latundData = noteBookService.saveLathundDataToDb(lathund);
        if(latundData == null){
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Lathundpost uploaded successful!", HttpStatus.CREATED);
    }

    @GetMapping("/lathund")
    public ResponseEntity getAllLathundData(){
        List<Lathund> latundData = noteBookService.getAllLathundDataFromDb();
        if(latundData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Lathundposts found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(latundData);
    }

    @DeleteMapping("/lathund/{id}")
    public ResponseEntity<String> deleteLathundDataById(@PathVariable Integer id){
            String response = noteBookService.deleteLathundPostById(id);
            if(response == null) {
                return new ResponseEntity<>("No Lathundposts found with that id!", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/linklibrary")
    public ResponseEntity<String> saveLinkLibraryData(@RequestBody LinkLibrary linkLibrary){
        LinkLibrary linkLibraryData = noteBookService.saveLinkLibraryDataToDb(linkLibrary);
        if(linkLibraryData == null){
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Linkpost uploaded successful!", HttpStatus.CREATED);
    }

    @GetMapping("/linklibrary")
    public ResponseEntity getAllLinkLibraryData(){
        List<LinkLibrary> linkLibraryData = noteBookService.getAllLinkLibraryDataFromDb();
        if(linkLibraryData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Linkposts found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(linkLibraryData);
    }

    @DeleteMapping("/linklibrary/{id}")
    public ResponseEntity<String> deletelinkLibraryDataById(@PathVariable Integer id){
        String response = noteBookService.deleteLinkLibraryPostById(id);
        if(response == null) {
            return new ResponseEntity<>("No LinkLibraryposts found with that id!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
