package com.footballstanding.footballstanding.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Standings {
    
    String country_name;
    String league_id;
    String league_name;
    String team_id;
    String team_name;
    String overall_promotion;
    String overall_league_position;
    String overall_league_payed;
    String overall_league_W;
    String overall_league_D;
    String overall_league_L;
    String overall_league_GF;
    String overall_league_GA;
    String overall_league_PTS;
    String home_league_position;
    String home_promotion;
    String home_league_payed;
    String home_league_W;
    String home_league_D;
    String home_league_L;
    String home_league_GF;
    String home_league_GA;
    String home_league_PTS;
    String away_league_position;
    String away_promotion;
    String away_league_payed;
    String away_league_W;
    String away_league_D;
    String away_league_L;
    String away_league_GF;
    String away_league_GA;
    String away_league_PTS;
    String league_round;
    String fk_stage_key;
    String stage_name;
}
