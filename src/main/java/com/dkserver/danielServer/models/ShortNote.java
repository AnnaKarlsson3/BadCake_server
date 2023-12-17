package com.dkserver.danielServer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="shortnotes")
public class ShortNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name="short_description")
    private String shortDescription;
    private String description;
    @Column(name="user_id")
    private String userId;

}
