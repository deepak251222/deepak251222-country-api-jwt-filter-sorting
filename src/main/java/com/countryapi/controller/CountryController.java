package com.countryapi.controller;

import com.countryapi.dto.ResponseData;
import com.countryapi.dto.ResponseFilterDto;
import com.countryapi.service.impl.CountryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/country")
public class CountryController {
	@Autowired
	private CountryServiceImpl countryServiceImpl;

	@GetMapping
	public ResponseEntity<?> getAllCountryDetails()
	{
		return  ResponseEntity.status(HttpStatus.OK).body(countryServiceImpl.getAllCountryDetails());
	}
	@GetMapping("/{name}")
	public ResponseEntity<?> getAllCountryDetails(@PathVariable String name)
	{
		return  ResponseEntity.status(HttpStatus.OK).body(countryServiceImpl.getCountry(name));
	}
	// we have to pass data by form into postman
	@GetMapping("/filter")
	public ResponseEntity<List<ResponseFilterDto>> getAllCountries(
			@RequestParam(name = "sortBy", defaultValue = "") String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize
	) {
		return ResponseEntity.status(HttpStatus.OK).body(countryServiceImpl.getAllCountriesFilter(sortBy, sortOrder, page, pageSize));
	}
	//    Or we can pass value by url
	@GetMapping("/filter/{filed}/{sortOrder}/{page}/{pageSize}")
	public ResponseEntity<List<ResponseFilterDto>> getAllCountriesFiltered(
			@PathVariable(name = "filed") String filed,
			@PathVariable(name = "sortOrder", required = false) String sortOrder,
			@PathVariable(name = "page",  required = false) int page,
			@PathVariable(name = "pageSize",  required = false) int pageSize
	) {
		List<ResponseFilterDto> filteredCountries = countryServiceImpl.getAllCountriesFilter(filed, sortOrder, page, pageSize);
		return ResponseEntity.ok(filteredCountries);
	}

}
