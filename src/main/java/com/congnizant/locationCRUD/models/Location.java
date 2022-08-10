package com.congnizant.locationCRUD.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "location")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "location_name", unique = true, length = 50)
    private String name;

    @Column(name = "location_zipcode", length = 50)
    private String zipcode;

    @Transient
    private double distance;

}
