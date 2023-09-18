package com.countryapi.service.impl;

import com.countryapi.dto.ResponseData;
import com.countryapi.dto.ResponseFilterDto;
import com.countryapi.exception.ResourceNotFoundException;
import com.countryapi.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;

@Service
public class CountryServiceImpl implements CountryService {

	//get all countries json response customize data
	@Override
	public List<ResponseData> getAllCountryDetails() {
		String baseUri = "https://restcountries.com/v3.1/all";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri);
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = builder.toUriString();
		ResponseEntity<ResponseData[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), ResponseData[].class);
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			ResponseData[] responseData = responseEntity.getBody();
			if (responseData != null && responseData.length > 0) {
				List<ResponseData> resultList = new ArrayList<>();
				for (ResponseData data : responseData) {
					resultList.add(data);
				}
				return resultList;
			}
		}
		throw new ResourceNotFoundException("Countries not found");
	}
	// search country by country name
	@Override
	public Object getCountry(String name) {
		String baseUri = "https://restcountries.com/v3.1/name/{name}";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri)
				.queryParam("name", name);

		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = builder.buildAndExpand(name).toUriString();

		try {
			ResponseEntity<Object> responseEntity = restTemplate.getForEntity(apiUrl, Object.class);
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				return responseEntity.getBody();
			}
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new ResourceNotFoundException("Countries not found");
		}
		throw new ResourceNotFoundException("Countries not found");
	}
	@Override
	public List<ResponseFilterDto> getAllCountriesFilter(String field, String sortOrder, int page, int pageSize) {

		List<ResponseFilterDto> allData=getAllCountriesFilter(field,sortOrder);
		int startIndex = (page - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, allData.size());
		if (startIndex >= allData.size()) {
			return Collections.emptyList();
		}
		return allData.subList(startIndex, endIndex);
	}
	//filtering data
	public List<ResponseFilterDto> getAllCountriesFilter(String field, String sortOrder) {
		String baseUri = "https://restcountries.com/v3.1/all";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUri)
				.queryParam("fields,name", field);
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = builder.toUriString();
		ResponseEntity<ResponseFilterDto[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), ResponseFilterDto[].class);
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			ResponseFilterDto[] responseData = responseEntity.getBody();
			if (responseData != null && responseData.length > 0) {
				List<ResponseFilterDto> resultList = new ArrayList<>();
				for (ResponseFilterDto data : responseData) {
					resultList.add(data);
				}
				List<ResponseFilterDto> sorting = sorting(field,sortOrder, resultList);
				return  sorting;
			}
		}
		throw new ResourceNotFoundException("Countries not found");
	}
	// shorting
	public List<ResponseFilterDto> sorting(String field,String sortOrder, List<ResponseFilterDto> resultList) {
		if (field.contains("population")) {
			for(ResponseFilterDto responseFilterDto : resultList){
				responseFilterDto.setArea(null);
				responseFilterDto.setLanguages(null);
				responseFilterDto.setLanguages(null);
				responseFilterDto.setCountry(responseFilterDto.getName().getCommon());
				responseFilterDto.setName(null);
			}
			if ("asc".equals(sortOrder) || sortOrder == null) {
				Comparator<ResponseFilterDto> ascendingComparator = Comparator.comparing(dto -> dto.getPopulation());
				Collections.sort(resultList, ascendingComparator);
			} else if ("desc".equals(sortOrder)) {
				Comparator<ResponseFilterDto> descendingComparator = Comparator.comparing(dto -> dto.getPopulation());
				resultList.sort(descendingComparator.reversed());
			}
			return resultList;
		}
		if (field.contains("area")) {
			for(ResponseFilterDto responseFilterDto : resultList){
				responseFilterDto.setPopulation(null);
				responseFilterDto.setLanguages(null);
				responseFilterDto.setLanguages(null);
				responseFilterDto.setCountry(responseFilterDto.getName().getCommon());
				responseFilterDto.setName(null);
			}
			if ("asc".equals(sortOrder) || sortOrder == null) {
				Comparator<ResponseFilterDto> ascendingComparator = Comparator.comparing(dto -> dto.getArea());
				Collections.sort(resultList, ascendingComparator);
			} else if ("desc".equals(sortOrder)) {
				Comparator<ResponseFilterDto> descendingComparator = Comparator.comparing(dto -> dto.getArea());
				resultList.sort(descendingComparator.reversed());
			}
			return resultList;
		}
		if (field.contains("languages")) {
			for(ResponseFilterDto responseFilterDto : resultList){
				responseFilterDto.setPopulation(null);
				responseFilterDto.setArea(null);
				responseFilterDto.setCountry(responseFilterDto.getName().getCommon());
				responseFilterDto.setName(null);
			}
			return resultList;
		}
		throw new ResourceNotFoundException("field name does not existed");
		//return resultList;
	}
}




