package com.congnizant.locationCRUD.dao;

import com.congnizant.locationCRUD.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findByName(String name);

    @Query(value = "SELECT l.location_zipcode FROM location l", nativeQuery = true)
    List<String> findAllZipcodes();

}
