package com.congnizant.locationCRUD.service;

import com.congnizant.locationCRUD.dao.LocationRepository;
import com.congnizant.locationCRUD.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    public Location findByName(String name){
        Location location=locationRepository.findByName(name);
        if(location == null){
            throw new EntityNotFoundException(name + " does not exist");
        }
        return location;
    }
}
