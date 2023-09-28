package com.footballstanding.footballstanding.service;

import org.springframework.http.ResponseEntity;

import com.footballstanding.footballstanding.model.Standings;

public interface FootballStandingService {
    
    ResponseEntity<Standings> getStandings(String countryName, String leagueName, String teamName);
}
