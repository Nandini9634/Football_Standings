package com.footballstanding.footballstanding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.footballstanding.footballstanding.constant.Endpoints;
import com.footballstanding.footballstanding.model.Standings;
import com.footballstanding.footballstanding.service.FootballStandingCacheService;
import com.footballstanding.footballstanding.service.FootballStandingService;

@RestController
@RequestMapping(Endpoints.API)
public class FootballStandingController {

    @Autowired
    @Qualifier("footballStandingServiceImpl")
    private FootballStandingService footballStandingService;

    @Autowired
    private FootballStandingCacheService footballStandingCacheService;

    @GetMapping(Endpoints.VERSION_1 + Endpoints.COUNTRIES)
    public ResponseEntity<Standings>  getStandings(@RequestParam(value = "countryName") String countryName, @RequestParam(value = "leagueName") String leagueName, @RequestParam(value = "teamName") String teamName, @RequestParam(value = "isClientOffline") Boolean isClientOffline) {

        if (isClientOffline) {
            
            // Return cached data if clinet is offline.
            return footballStandingCacheService.getStandings(countryName, leagueName, teamName);
        } else {
            return footballStandingService.getStandings(countryName, leagueName, teamName);
        }
    }
}
