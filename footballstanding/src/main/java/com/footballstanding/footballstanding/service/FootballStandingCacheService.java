package com.footballstanding.footballstanding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.footballstanding.footballstanding.model.Standings;

@Service
public class FootballStandingCacheService implements FootballStandingService{

    @Autowired
    private CacheManager cacheManager;

    public FootballStandingCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public ResponseEntity<Standings> getStandings(String countryName, String leagueName, String teamName) {

        // Check if the data is in the cache
        Cache cache = cacheManager.getCache("footballStandingApiDataCache");

        if(cache != null) {
            ValueWrapper cachedData = cache.get(countryName + '-' + leagueName + '-' + teamName);

            if (cachedData != null) {
                // Data is available in the cache, return it
                return (ResponseEntity<Standings>) cachedData.get();
            } else {
                // Data is not in the cache, and user is offline
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
