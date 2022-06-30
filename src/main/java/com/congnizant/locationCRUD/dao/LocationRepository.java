package com.congnizant.locationCRUD.dao;

import com.congnizant.locationCRUD.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {
    Location findByName(String name);
}
