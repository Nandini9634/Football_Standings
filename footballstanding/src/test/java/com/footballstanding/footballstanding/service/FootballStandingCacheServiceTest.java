package com.footballstanding.footballstanding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.footballstanding.footballstanding.model.FootballStanding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FootballStandingCacheServiceTest {

  @Mock
  private CacheManager cacheManager;

  @Mock
  private Cache cache;

  private FootballStandingCacheService cacheService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    cacheService = new FootballStandingCacheService(cacheManager);
  }

  @Test
  public void testGetStandingsDataInCache() {
    String countryName = "Country1";
    String leagueName = "League1";
    String teamName = "Team1";
    ResponseEntity<Object> cachedResponse = ResponseEntity.ok("Cached Data");

    when(cacheManager.getCache("footballStandingApiDataCache"))
      .thenReturn(cache);
    when(cache.get(countryName + '-' + leagueName + '-' + teamName))
      .thenReturn(() -> cachedResponse);

    ResponseEntity<FootballStanding> result = cacheService.getStandings(
      countryName,
      leagueName,
      teamName
    );

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("Cached Data", result.getBody());
  }

  @Test
  public void testGetStandingsDataNotInCache() {
    String countryName = "Country2";
    String leagueName = "League2";
    String teamName = "Team2";

    when(cacheManager.getCache("footballStandingApiDataCache"))
      .thenReturn(cache);
    when(cache.get(countryName + '-' + leagueName + '-' + teamName))
      .thenReturn(null);

    ResponseEntity<FootballStanding> result = cacheService.getStandings(
      countryName,
      leagueName,
      teamName
    );

    assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());
  }

  @Test
  public void testGetStandingsCacheNotAvailable() {
    String countryName = "Country3";
    String leagueName = "League3";
    String teamName = "Team3";

    when(cacheManager.getCache("footballStandingApiDataCache"))
      .thenReturn(null);

    ResponseEntity<FootballStanding> result = cacheService.getStandings(
      countryName,
      leagueName,
      teamName
    );

    assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());
  }
}
