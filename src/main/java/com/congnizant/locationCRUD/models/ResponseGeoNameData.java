package com.congnizant.locationCRUD.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGeoNameData {
    private List<GeonameData> postalCodes;
}
