package com.footballstanding.footballstanding.service;

import com.footballstanding.footballstanding.model.FootballStanding;
import org.springframework.http.ResponseEntity;

public interface FootballStandingService {
  ResponseEntity<FootballStanding> getStandings(
    String countryName,
    String leagueName,
    String teamName
  );
}
