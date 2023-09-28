package com.footballstanding.footballstanding.accessor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.footballstanding.footballstanding.config.RestTemplateConfig;
import com.footballstanding.footballstanding.constant.Endpoints;
import com.footballstanding.footballstanding.exception.ApiAccessException;
import com.footballstanding.footballstanding.exception.ResourceNotFoundException;
import com.footballstanding.footballstanding.model.Country;
import com.footballstanding.footballstanding.model.League;
import com.footballstanding.footballstanding.model.Standings;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FootballStandingAccessorImpl implements FootballStandingAccessor {

    private final String apiKey;

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    public FootballStandingAccessorImpl(@Value("${api.key}") String apiKey, RestTemplateConfig restTemplateConfig) {
        this.apiKey = apiKey;
        this.restTemplateConfig = restTemplateConfig;
    }
    
    @Override
    public List<Country> getCountries() {

        RestTemplate restTemplate = restTemplateConfig.getRestTemplateClient();

        final String countriesUri = Endpoints.FOOTBALL_API + Endpoints.QUESTION_MARK + Endpoints.ACTION + Endpoints.EQUALS + Endpoints.GET_COUNTRIES_ACTION + Endpoints.AND + Endpoints.API_KEY + Endpoints.EQUALS + apiKey;

        log.info("Calling API to get countries via API: " + countriesUri);

        ParameterizedTypeReference<List<Country>> responseType = new ParameterizedTypeReference<List<Country>>() {};

        try {
            
            ResponseEntity<List<Country>> responseEntity = restTemplate.exchange(countriesUri, HttpMethod.GET, null, responseType);

            List<Country> countryList = responseEntity.getBody();

            log.info("Countries list obtained: " + countryList);
            return countryList;  
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Resource not found. Exception message: " + e);
        } catch (HttpServerErrorException e) {
            // Handle 5xx server errors
            throw new ApiAccessException("Server error. Exception message: ", e);
        } catch (RestClientException e) {
            // Handle other RestTemplate related exceptions, including SocketTimeoutException
            throw new ApiAccessException("Error accessing the API. Exception message: ", e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new ApiAccessException("Unexpected error. Exception message: ", e);
        }
    }

    @Override
    public List<League> getLeagues(String countryId) {

        RestTemplate restTemplate = restTemplateConfig.getRestTemplateClient();

        final String leaguesUri = Endpoints.FOOTBALL_API + Endpoints.QUESTION_MARK + Endpoints.ACTION + Endpoints.EQUALS + Endpoints.GET_LEAGUES_ACTION + Endpoints.AND + Endpoints.COUNTRY_ID + Endpoints.EQUALS + countryId + Endpoints.AND + Endpoints.API_KEY + Endpoints.EQUALS + apiKey;

        log.info("Calling API to get leagues via API: " + leaguesUri);
        
        ParameterizedTypeReference<List<League>> responseType = new ParameterizedTypeReference<List<League>>() {};

        try {
            
            ResponseEntity<List<League>> responseEntity = restTemplate.exchange(leaguesUri, HttpMethod.GET, null, responseType);

            List<League> leagueList = responseEntity.getBody();

            log.info("Leagues list obtained: " + leagueList);
            return leagueList;

        }  catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Resource not found. Exception message: " + e);
        } catch (HttpServerErrorException e) {
            // Handle 5xx server errors
            throw new ApiAccessException("Server error. Exception message: ", e);
        } catch (RestClientException e) {
            // Handle other RestTemplate related exceptions, including SocketTimeoutException
            throw new ApiAccessException("Error accessing the API. Exception message: ", e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new ApiAccessException("Unexpected error. Exception message: ", e);
        }
    }


    @Override
    public List<Standings> getStandings(String leagueId) {

        RestTemplate restTemplate = restTemplateConfig.getRestTemplateClient();

        final String standingsUri = Endpoints.FOOTBALL_API + Endpoints.QUESTION_MARK + Endpoints.ACTION + Endpoints.EQUALS + Endpoints.GET_STANDINGS_ACTION + Endpoints.AND + Endpoints.LEAGUE_ID + Endpoints.EQUALS + leagueId + Endpoints.AND + Endpoints.API_KEY + Endpoints.EQUALS + apiKey;

        log.info("Calling API to get standings via API: " + standingsUri);
        
        ParameterizedTypeReference<List<Standings>> responseType = new ParameterizedTypeReference<List<Standings>>() {};

        try {

            ResponseEntity<List<Standings>> responseEntity = restTemplate.exchange(standingsUri, HttpMethod.GET, null, responseType);

            List<Standings> standingsList = responseEntity.getBody();

            log.info("Standings list obtained: " + standingsList);
            return standingsList;

        }  catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Resource not found. Exception message: " + e);
        } catch (HttpServerErrorException e) {
            // Handle 5xx server errors
            throw new ApiAccessException("Server error. Exception message: ", e);
        } catch (RestClientException e) {
            // Handle other RestTemplate related exceptions, including SocketTimeoutException
            throw new ApiAccessException("Error accessing the API. Exception message: ", e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new ApiAccessException("Unexpected error. Exception message: ", e);
        }
    }
}
