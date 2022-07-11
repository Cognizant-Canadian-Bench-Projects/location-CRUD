package com.congnizant.locationCRUD.service;

import com.congnizant.locationCRUD.dao.LocationRepository;
import com.congnizant.locationCRUD.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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

    public Location findById(int id){
        Optional<Location> location=locationRepository.findById(id);
        if(!location.isPresent()){
            throw new EntityNotFoundException("location with id "+ id + " does not exist");
        }
        return location.get();
    }
}
