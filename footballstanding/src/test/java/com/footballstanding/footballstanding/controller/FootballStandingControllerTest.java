package com.footballstanding.footballstanding.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.footballstanding.footballstanding.model.Standings;
import com.footballstanding.footballstanding.service.FootballStandingCacheService;
import com.footballstanding.footballstanding.service.FootballStandingService;

@MockitoSettings
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

        Standings cachedStandings = new Standings();
        when(footballStandingCacheService.getStandings(countryName, leagueName, teamName))
                .thenReturn(new ResponseEntity<>(cachedStandings, HttpStatus.OK));

        ResponseEntity<Standings> response = footballStandingController.getStandings(countryName, leagueName, teamName, isClientOffline);

        verify(footballStandingCacheService, times(1)).getStandings(countryName, leagueName, teamName);

        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody()).equals(cachedStandings);
    }

    @Test
    public void testGetStandingsWhenClientIsOnline() {

        String countryName = "Country2";
        String leagueName = "League2";
        String teamName = "Team2";
        Boolean isClientOffline = false;


        Standings serviceStandings = new Standings();
        when(footballStandingService.getStandings(countryName, leagueName, teamName))
                .thenReturn(new ResponseEntity<>(serviceStandings, HttpStatus.OK));

        ResponseEntity<Standings> response = footballStandingController.getStandings(countryName, leagueName, teamName, isClientOffline);

        verify(footballStandingService, times(1)).getStandings(countryName, leagueName, teamName);

        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody()).equals(serviceStandings);
    }
}
