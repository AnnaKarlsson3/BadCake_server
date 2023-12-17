package com.dkserver.danielServer.controllers;


import com.dkserver.danielServer.models.Customer;
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

    @Autowired
    NoteBookService noteBookService;

    @PostMapping("/customer")
    public ResponseEntity<String> saveCustomerData(@RequestBody Customer customer){
        Customer customerData = noteBookService.saveCustomerDataToDb(customer);
        if(customer == null){
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Customer post uploaded successful!", HttpStatus.CREATED);
    }
    @GetMapping("/customer")
    public ResponseEntity getAllCustomerData(){
        List<Customer> customerData = noteBookService.getAllCustomerDataFromDb();
        if(customerData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customersfound!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerData);
    }

    @PostMapping("/shortnote")
    public ResponseEntity<String> saveLathundData(@RequestBody ShortNote shortNote){
        ShortNote latundData = noteBookService.saveLathundDataToDb(shortNote);
        if(latundData == null){
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("shortnote post uploaded successful!", HttpStatus.CREATED);
    }

    @GetMapping("/shortnote")
    public ResponseEntity getAllLathundData(){
        List<ShortNote> latundData = noteBookService.getAllLathundDataFromDb();
        if(latundData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No shortNotes posts found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(latundData);
    }

    @DeleteMapping("/shortnote/{id}")
    public ResponseEntity<String> deleteLathundDataById(@PathVariable Integer id){
            String response = noteBookService.deleteLathundPostById(id);
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
