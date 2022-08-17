package com.congnizant.locationCRUD.controller;

import com.congnizant.locationCRUD.models.Location;
import com.congnizant.locationCRUD.models.ResponseGeoNameData;
import com.congnizant.locationCRUD.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

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
  @Retryable(maxAttempts = 3,value = RestClientException.class, backoff = @Backoff(delay = 500, multiplier = 2))

  public ResponseEntity<?> getZipcodes(@RequestParam String zipcode, @RequestParam String country, @RequestParam int radius) {
    String url = "http://api.geonames.org/findNearbyPostalCodesJSON?postalcode=" +
        zipcode + "&country=" +
        country + "&radius=" +
        radius + "&username=deepakAgarwal";
    RestTemplate restTemplate = new RestTemplate();
    ResponseGeoNameData geonameData = restTemplate.getForObject(url, ResponseGeoNameData.class);

    return ResponseEntity.ok(locationService.addDistances(geonameData));
  }
  @Recover
  public ResponseEntity<String> geonameRecovery(Exception e){
    return new ResponseEntity<String>(e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR );
  }
}
