package com.congnizant.locationCRUD.service;

import com.congnizant.locationCRUD.dao.LocationRepository;
import com.congnizant.locationCRUD.models.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    LocationService locationService;

    Location location1;
    Location location2;
    Location location3;
    Location location4;


    @BeforeEach
    public  void init(){
        location1 =new Location(1,"toronto","m1p3r1t");
        location2 =new Location(1,"northyork","m1p6r1t");
        location3 =new Location(1,"brampton","m1p4r4t");
        location4 =new Location(1,"scarborough","m1p6r1t");
    }



    @Test
    void findByName_Positive() {
        when(locationRepository.findByName("toronto")).thenReturn(location1);
        Location actual=locationService.findByName("toronto");
        assertThat(actual).isEqualTo(location1);
    }

    @Test
    void findByName_Negative(){
        Assertions.assertThrows(EntityNotFoundException.class,() -> {
            locationService.findByName("");
        });
    }

  @Test
  void findById_Positive() {
      when(locationRepository.findById(1)).thenReturn(Optional.of(location1));
      Location actual=locationService.findById(1);
      assertThat(actual).isEqualTo(location1);
  }

    @Test
    void findById_Negative(){
        Assertions.assertThrows(EntityNotFoundException.class,() -> {
            locationService.findById(1);
        });
    }
}