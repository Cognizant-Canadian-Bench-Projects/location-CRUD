package com.congnizant.locationCRUD.controller;

import com.congnizant.locationCRUD.models.Location;
import com.congnizant.locationCRUD.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    Location location1;

    @BeforeEach
    void setUp() {
        location1=new Location(1,"toronto","m1p3t1");
    }

    @Test
    void getLocation() {
        locationController.getLocation("toronto");
        verify(locationService,times(1)).findByName("toronto");
    }
    @Test
    void  getLocation_Positive(){
        when(locationService.findByName("toronto")).thenReturn(location1);

        ResponseEntity actual =locationController.getLocation("toronto");
        assertThat(actual.getStatusCodeValue()).isEqualTo(200);
        assertThat(actual.getBody()).isEqualTo(location1);
    }

    @Test
    void getLocation_Negative(){
        when(locationService.findByName("scarborough")).thenThrow(EntityNotFoundException.class);

        ResponseEntity actual=locationController.getLocation("scarborough");
        assertThat(actual.getStatusCodeValue()).isEqualTo(404);
    }
}