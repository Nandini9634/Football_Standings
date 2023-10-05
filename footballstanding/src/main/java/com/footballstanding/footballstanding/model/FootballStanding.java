package com.footballstanding.footballstanding.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FootballStanding {
    
    String country_id;
    String country_name;
    String league_id;
    String league_name;
    String team_id;
    String team_name;
    String overall_league_position;
}
