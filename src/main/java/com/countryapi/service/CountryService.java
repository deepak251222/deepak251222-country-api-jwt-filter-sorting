package com.countryapi.service;

import com.countryapi.dto.ResponseData;
import com.countryapi.dto.ResponseFilterDto;

import java.util.List;

public interface CountryService {
    public List<ResponseData> getAllCountryDetails();
    public Object getCountry(String name);
    public List<ResponseFilterDto> getAllCountriesFilter(String field, String sortOrder, int page, int pageSize);
}
