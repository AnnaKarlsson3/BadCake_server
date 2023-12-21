package com.dkserver.danielServer.controllers;

import com.dkserver.danielServer.models.CustomerEntity;
import com.dkserver.danielServer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dkserver.danielServer.utils.Constants.*;

@RestController
@CrossOrigin(origins = CORS_URL)
@RequestMapping(REST)
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity saveCustomerData(@RequestBody CustomerEntity customerEntity){

        CustomerEntity customerEntityData = customerService.saveCustomerDataToDb(customerEntity);
        if(customerEntityData == null){
            return new ResponseEntity(ERROR_RESPONSE_SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(customerEntityData, HttpStatus.CREATED);
    }
    @GetMapping("/customer")
    public ResponseEntity getAllCustomerData(){
        List<CustomerEntity> customerEntityData = customerService.getAllCustomerDataFromDb();
        if(customerEntityData.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CUSTOMER_RESPONSE_NO_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerEntityData);
    }

}
