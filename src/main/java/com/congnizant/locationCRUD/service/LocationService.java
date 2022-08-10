package com.congnizant.locationCRUD.service;

import com.congnizant.locationCRUD.dao.LocationRepository;
import com.congnizant.locationCRUD.models.GeonameData;
import com.congnizant.locationCRUD.models.Location;
import com.congnizant.locationCRUD.models.ResponseGeoNameData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

//    public List<String> getAllZipcodes() {
//        List<String> allZipcode = locationRepository.findAllZipcodes();
//        if (allZipcode.isEmpty()) {
//            throw new EntityNotFoundException("Error");
//        }
//        System.out.println(allZipcode);
//        return allZipcode;
//    }
    public List<Location> addDistances(ResponseGeoNameData responseGeoNameData){
       List<Location> locations= this.getAllLocations();
//       locations.stream().filter(location -> responseGeoNameData.getPostalCodes().contains(location.getZipcode()))
        responseGeoNameData.getPostalCodes().stream().filter(geonameData -> locations.stream().anyMatch(location -> location.getZipcode().equals(geonameData.getPostalCode()))).forEach(geonameData ->
                        locations.stream().flatMap(location -> {
                            if(location.getZipcode().equals(geonameData.getPostalCode())){
                                location.setDistance(geonameData.getDistance());
                            }
                            return  null;
                        })
                );
        System.out.println(locations);
        return  null;


    }

}
