package com.congnizant.locationCRUD.controller;

import com.congnizant.locationCRUD.models.Location;
import com.congnizant.locationCRUD.models.ResponseGeoNameData;
import com.congnizant.locationCRUD.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(originPatterns = "*", exposedHeaders = "*", allowedHeaders = "*")
public class LocationController {
  @Autowired
  LocationService locationService;

  @GetMapping("/locations")
  @Cacheable(value = "locations")
  public ResponseEntity<?> getLocation(@RequestParam(required = false) String name) {
    try {
      if (name == null || name.equals("")) {
        return ResponseEntity.ok(locationService.getAllLocations());

      } else {
        Location location = locationService.findByName(name);
        return ResponseEntity.ok(location);
      }

    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @GetMapping("/locations/{id}")
  @Cacheable(value = "locations", key = "#id")
  public ResponseEntity<?> getLocation(@PathVariable("id") int id) {
    try {
      Location location = locationService.findById(id);
      return ResponseEntity.ok(location);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @GetMapping("/geoname")
  public ResponseEntity<?> getZipcodes(@RequestParam String zipcode, @RequestParam String country, @RequestParam int radius) {
    String url = "http://api.geonames.org/findNearbyPostalCodesJSON?postalcode=" +
        zipcode + "&country=" +
        country + "&radius=" +
        radius + "&username=deepakAgarwal";
    RestTemplate restTemplate = new RestTemplate();
    ResponseGeoNameData geonameData = restTemplate.getForObject(url, ResponseGeoNameData.class);
    geonameData.getPostalCodes().forEach(data -> {
      if(data.getDistance() == 0){
        data.setDistance(0.00001);
      }
    });
    return ResponseEntity.ok(locationService.addDistances(geonameData));
  }

//    @GetMapping("/locations/zipcodes")
//    public ResponseEntity<?> getAllZipcodes() {
//        try {
//           return ResponseEntity.ok(locationService.getAllZipcodes());
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(404).body(e.getMessage());
//        }
//    }
}
