package com.congnizant.locationCRUD.service;

import com.congnizant.locationCRUD.dao.LocationRepository;
import com.congnizant.locationCRUD.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    public Location findByName(String name) {
        Location location = locationRepository.findByName(name);
        if (location == null) {
            throw new EntityNotFoundException(name + " does not exist");
        }
        System.out.println("call the database");
        return location;
    }

    public Location findById(int id) {
        Optional<Location> location = locationRepository.findById(id);
        if (!location.isPresent()) {
            throw new EntityNotFoundException("location with id " + id + " does not exist");
        }
        System.out.println("call the database");
        return location.get();
    }

    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    public List<String> getAllZipcodes() {
        List<String> allZipcode = locationRepository.findAllZipcodes();
        if (allZipcode.isEmpty()) {
            throw new EntityNotFoundException("Error");
        }
        System.out.println(allZipcode);
        return allZipcode;
    }
}
