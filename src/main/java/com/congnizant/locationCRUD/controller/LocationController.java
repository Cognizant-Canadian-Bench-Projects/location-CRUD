package com.congnizant.locationCRUD.controller;

import com.congnizant.locationCRUD.models.Location;
import com.congnizant.locationCRUD.service.LocationService;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;

@RestController
@CrossOrigin(originPatterns = "*", exposedHeaders = "*",allowedHeaders = "*")
public class LocationController {
    @Autowired
    LocationService locationService;

    @GetMapping("/locations")
    @Cacheable(value="locations", key="#name")
    public ResponseEntity<?> getLocation(@RequestParam String name){
        try{
            Location location=locationService.findByName(name);
            return  ResponseEntity.ok(location);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/locations/{id}")
    @Cacheable(value="locations", key="#id")
    public ResponseEntity<?> getLocation(@PathVariable("id") int id){
        try{
            Location location=locationService.findById(id);
            return  ResponseEntity.ok(location);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
