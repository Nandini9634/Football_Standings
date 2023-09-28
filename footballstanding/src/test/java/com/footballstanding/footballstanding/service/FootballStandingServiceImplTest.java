package com.footballstanding.footballstanding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.net.SocketTimeoutException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.footballstanding.footballstanding.accessor.FootballStandingAccessor;
import com.footballstanding.footballstanding.exception.ResourceNotFoundException;
import com.footballstanding.footballstanding.model.Country;
import com.footballstanding.footballstanding.model.League;
import com.footballstanding.footballstanding.model.Standings;

public class FootballStandingServiceImplTest {

    @Mock
    private FootballStandingAccessor footballStandingAccessor;

    private FootballStandingServiceImpl footballStandingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        footballStandingService = new FootballStandingServiceImpl(footballStandingAccessor);
    }

    @Test
    public void testGetStandingsSuccess() throws SocketTimeoutException {
        String countryName = "Country1";
        String leagueName = "League1";
        String teamName = "Team1";
        Standings expectedStandings = new Standings();
        expectedStandings.setCountry_name(countryName);
        expectedStandings.setLeague_name(leagueName);
        expectedStandings.setTeam_name(teamName);

        when(footballStandingAccessor.getCountries()).thenReturn(Arrays.asList(new Country("1", countryName)));
        when(footballStandingAccessor.getLeagues(anyString())).thenReturn(Arrays.asList(new League("C1", "Country1", "L1", "League1")));
        when(footballStandingAccessor.getStandings(anyString())).thenReturn(Arrays.asList(expectedStandings));

        ResponseEntity<Standings> responseEntity = footballStandingService.getStandings(countryName, leagueName, teamName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStandings, responseEntity.getBody());
    }

    @Test
    public void testGetStandingsCountryNotFound() throws SocketTimeoutException {
        String countryName = "NonExistentCountry";
        String leagueName = "League1";
        String teamName = "Team1";

        when(footballStandingAccessor.getCountries()).thenReturn(Arrays.asList(new Country("Country1", "1")));

        ResponseEntity<Standings> responseEntity = footballStandingService.getStandings(countryName, leagueName, teamName);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetStandingsLeagueNotFound() throws SocketTimeoutException {
        String countryName = "Country1";
        String leagueName = "NonExistentLeague";
        String teamName = "Team1";

        when(footballStandingAccessor.getCountries()).thenReturn(Arrays.asList(new Country(countryName, "1")));

        ResponseEntity<Standings> responseEntity = footballStandingService.getStandings(countryName, leagueName, teamName);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetStandingsStandingNotFound() throws SocketTimeoutException {
        String countryName = "Country1";
        String leagueName = "League1";
        String teamName = "NonExistentTeam";

        when(footballStandingAccessor.getCountries()).thenReturn(Arrays.asList(new Country(countryName, "1")));

        ResponseEntity<Standings> responseEntity = footballStandingService.getStandings(countryName, leagueName, teamName);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testGetStandingsCacheException() throws SocketTimeoutException {
        String countryName = "Country1";
        String leagueName = "League1";
        String teamName = "Team1";

        when(footballStandingAccessor.getCountries()).thenThrow(new ResourceNotFoundException("Resource not found"));

        ResponseEntity<Standings> responseEntity = footballStandingService.getStandings(countryName, leagueName, teamName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
