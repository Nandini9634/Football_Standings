package com.footballstanding.footballstanding.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.footballstanding.footballstanding.accessor.FootballStandingAccessor;
import com.footballstanding.footballstanding.exception.ApiAccessException;
import com.footballstanding.footballstanding.exception.ResourceNotFoundException;
import com.footballstanding.footballstanding.model.Country;
import com.footballstanding.footballstanding.model.League;
import com.footballstanding.footballstanding.model.Standings;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FootballStandingServiceImpl implements FootballStandingService {

    @Autowired
    private FootballStandingAccessor footballStandingAccessor;

    public FootballStandingServiceImpl(FootballStandingAccessor footballStandingAccessor) {
        this.footballStandingAccessor = footballStandingAccessor;
    }

    @Override
    @Cacheable(value = "footballStandingApiDataCache", key = "#countryName + '-' + #leagueName + '-' + #teamName")
    public ResponseEntity<Standings> getStandings(String countryName, String leagueName, String teamName) {

        try {

            Optional<String> countryId = getCountryId(countryName);

            if (countryId.isPresent()) {
                log.info("Country ID for " + countryName + ": " + countryId.get());
            } else {
                log.info("Country not found: " + countryName);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<String> leagueId = getLeagueId(countryId.get(), leagueName);

            if (leagueId.isPresent()) {
                log.info("League ID for " + leagueName + ": " + leagueId.get());
            } else {
                log.info("League not found: " + leagueName);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<Standings> standingsList = footballStandingAccessor.getStandings(leagueId.get());

            Optional<Standings> standing = standingsList.stream()
            .filter(standings -> countryName.equals(standings.getCountry_name())
                    && leagueName.equals(standings.getLeague_name())
                    && teamName.equals(standings.getTeam_name()))
            .findFirst();

            if (standing.isPresent()) {
                log.info("Standing for country: " + countryName + "league: " + leagueName + "team: " + teamName + "is: " + standing.get());
            } else {
                log.info("standing not found for country: " + countryName + "league: " + leagueName + "team: " + teamName);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            return new ResponseEntity<Standings>(standing.get(), HttpStatus.OK);
        }  catch (HttpClientErrorException.NotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Optional<String> getCountryId(String countryName) {

        try {

            List<Country> countryList = footballStandingAccessor.getCountries();

            Optional<String> countryId = countryList.stream()
                .filter(country -> countryName.equals(country.getCountry_name()))
                .map(Country::getCountry_id)
                .findFirst();

            return countryId;
        }  catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Resource not found. Exception message: " + e);
        } catch (Exception e) {
            throw new ApiAccessException("Error accessing the API. Exception message: ", e);
        }
    }

    private Optional<String> getLeagueId(String countryId, String leagueName) {

        try {

            List<League> leagueList = footballStandingAccessor.getLeagues(countryId);

            Optional<String> leagueId = leagueList.stream()
                .filter(country -> leagueName.equals(country.getLeague_name()))
                .map(League::getLeague_id)
                .findFirst();

            return leagueId;
        }  catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Resource not found. Exception message: " + e);
        } catch (Exception e) {
            throw new ApiAccessException("Error accessing the API. Exception message: ", e);
        }
    }
}
