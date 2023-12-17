package com.dkserver.danielServer.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private String userId;
    private String name;
    @Column(name="org_nr")
    private String orgNr;
    private String contact;
    @Column(name="contact_phone")
    private String contactPhone;
    @Column(name="admin_name")
    private String adminName;
    @Column(name="admin_password")
    private String adminPassword;
    private String address;
    @Column(name="post_no")
    private String postNo;
    private String city;

}