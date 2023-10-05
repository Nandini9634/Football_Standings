package com.footballstanding.footballstanding.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.footballstanding.footballstanding.model.FootballStanding;
import com.footballstanding.footballstanding.service.FootballStandingCacheService;
import com.footballstanding.footballstanding.service.FootballStandingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FootballStandingControllerTest {

  @InjectMocks
  private FootballStandingController footballStandingController;

  @Mock
  private FootballStandingService footballStandingService;

  @Mock
  private FootballStandingCacheService footballStandingCacheService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetStandingsWhenClientIsOffline() {
    String countryName = "Country1";
    String leagueName = "League1";
    String teamName = "Team1";
    Boolean isClientOffline = true;

    FootballStanding cachedStandings = new FootballStanding();
    when(
      footballStandingCacheService.getStandings(
        countryName,
        leagueName,
        teamName
      )
    )
      .thenReturn(new ResponseEntity<>(cachedStandings, HttpStatus.OK));

    ResponseEntity<FootballStanding> response = footballStandingController.getStandings(
      countryName,
      leagueName,
      teamName,
      isClientOffline
    );

    verify(footballStandingCacheService, times(1))
      .getStandings(countryName, leagueName, teamName);

    assert (response.getStatusCode()).equals(HttpStatus.OK);
    assert (response.getBody()).equals(cachedStandings);
  }

  @Test
  public void testGetStandingsWhenClientIsOnline() {
    String countryName = "Country2";
    String leagueName = "League2";
    String teamName = "Team2";
    Boolean isClientOffline = false;

    FootballStanding serviceStandings = new FootballStanding();
    when(
      footballStandingService.getStandings(countryName, leagueName, teamName)
    )
      .thenReturn(new ResponseEntity<>(serviceStandings, HttpStatus.OK));

    ResponseEntity<FootballStanding> response = footballStandingController.getStandings(
      countryName,
      leagueName,
      teamName,
      isClientOffline
    );

    verify(footballStandingService, times(1))
      .getStandings(countryName, leagueName, teamName);

    assert (response.getStatusCode()).equals(HttpStatus.OK);
    assert (response.getBody()).equals(serviceStandings);
  }
}
