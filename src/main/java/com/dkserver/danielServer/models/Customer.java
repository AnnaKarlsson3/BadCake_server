package com.dkserver.danielServer.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userId;
    private String name;
    private String orgNr;
    private String contact;
    private String contactPhone;
    private String adminName;
    private String adminPassword;
    private String address;
    private String postNo;
    private String city;

}