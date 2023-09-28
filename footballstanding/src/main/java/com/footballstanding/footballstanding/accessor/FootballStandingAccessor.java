package com.footballstanding.footballstanding.accessor;

import java.net.SocketTimeoutException;
import java.util.List;

import com.footballstanding.footballstanding.model.Country;
import com.footballstanding.footballstanding.model.League;
import com.footballstanding.footballstanding.model.Standings;

public interface FootballStandingAccessor {

    public List<Country> getCountries() throws SocketTimeoutException;
    public List<League> getLeagues(String countryId);
    public List<Standings> getStandings(String leagueId);
    
}
