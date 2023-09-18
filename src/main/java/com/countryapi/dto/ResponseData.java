package com.countryapi.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
	private Name name;
	private double area;
	private long population;
	private Map<String, String> languages;

}