package com.dkserver.danielServer.services;

import com.dkserver.danielServer.models.CustomerEntity;
import com.dkserver.danielServer.repository.CustomerRepo;
import com.dkserver.danielServer.utils.GetLoggedInUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class CustomerService {

    @Autowired
    GetLoggedInUser getLoggedInUser;

    @Autowired
    CustomerRepo customerRepo;

    public CustomerEntity saveCustomerDataToDb(CustomerEntity customerEntity) {
        customerEntity.setUserId(getLoggedInUser.getLoggedInUserId());
        return customerRepo.save(customerEntity);
    }

    public List<CustomerEntity> getAllCustomerDataFromDb() {
        return customerRepo.findAllByUserId(getLoggedInUser.getLoggedInUserId());
    }
}
