package com.footballstanding.footballstanding.accessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.footballstanding.footballstanding.config.RestTemplateConfig;
import com.footballstanding.footballstanding.exception.ApiAccessException;
import com.footballstanding.footballstanding.model.Country;
import com.footballstanding.footballstanding.model.League;
import com.footballstanding.footballstanding.model.Standings;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class FootballStandingAccessorTest {

  private FootballStandingAccessor footballStandingAccessor;

  @Mock
  private RestTemplateConfig restTemplateConfig;

  @Mock
  private RestTemplate restTemplate;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    footballStandingAccessor =
      new FootballStandingAccessorImpl(getApiKey(), restTemplateConfig);
    when(restTemplateConfig.getRestTemplateClient()).thenReturn(restTemplate);
  }

  @Test
  public void testGetCountriesSuccess() throws SocketTimeoutException {
    List<Country> expectedCountries = Arrays.asList(
      new Country("1", "Country1"),
      new Country("2", "Country2")
    );

    when(
      restTemplate.exchange(
        any(String.class),
        any(HttpMethod.class),
        any(),
        any(ParameterizedTypeReference.class)
      )
    )
      .thenReturn(new ResponseEntity<>(expectedCountries, HttpStatus.OK));

    List<Country> countries = footballStandingAccessor.getCountries();
    assertNotNull(countries);
    assertEquals(expectedCountries.size(), countries.size());
  }

  @Test
  public void testGetLeaguesSuccess() {
    String countryId = "C1";
    List<League> expectedLeagues = Arrays.asList(
      new League("C1", "Country1", "L1", "League1"),
      new League("C2", "Country2", "L2", "League2")
    );

    when(
      restTemplate.exchange(
        any(String.class),
        any(HttpMethod.class),
        any(),
        any(ParameterizedTypeReference.class)
      )
    )
      .thenReturn(new ResponseEntity<>(expectedLeagues, HttpStatus.OK));

    List<League> leagues = footballStandingAccessor.getLeagues(countryId);
    assertNotNull(leagues);
    assertEquals(expectedLeagues.size(), leagues.size());
  }

  @Test
  public void testGetStandingsSuccess() {
    String leagueId = "1";
    List<Standings> expectedStandings = Arrays.asList(
      new Standings(),
      new Standings()
    );

    when(
      restTemplate.exchange(
        any(String.class),
        any(HttpMethod.class),
        any(),
        any(ParameterizedTypeReference.class)
      )
    )
      .thenReturn(new ResponseEntity<>(expectedStandings, HttpStatus.OK));

    List<Standings> standings = footballStandingAccessor.getStandings(leagueId);
    assertNotNull(standings);
    assertEquals(expectedStandings.size(), standings.size());
  }

  @Test
  public void testGetCountriesNotFound() {
    when(
      restTemplate.exchange(
        any(String.class),
        any(HttpMethod.class),
        any(),
        any(ParameterizedTypeReference.class)
      )
    )
      .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

    assertThrows(
      ApiAccessException.class,
      () -> footballStandingAccessor.getCountries()
    );
  }

  @Test
  public void testGetLeaguesServerError() {
    when(
      restTemplate.exchange(
        any(String.class),
        any(HttpMethod.class),
        any(),
        any(ParameterizedTypeReference.class)
      )
    )
      .thenThrow(new RestClientException("Internal Server Error"));

    assertThrows(
      ApiAccessException.class,
      () -> footballStandingAccessor.getLeagues("1")
    );
  }

  @Test
  public void testGetStandingsOtherException() {
    when(
      restTemplate.exchange(
        any(String.class),
        any(HttpMethod.class),
        any(),
        any(ParameterizedTypeReference.class)
      )
    )
      .thenThrow(new IllegalArgumentException("Some other exception"));

    assertThrows(
      ApiAccessException.class,
      () -> footballStandingAccessor.getStandings("1")
    );
  }

  private String getApiKey() {
    return "abc123";
  }
}
