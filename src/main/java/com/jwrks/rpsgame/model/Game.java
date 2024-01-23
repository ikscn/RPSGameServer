package com.jwrks.rpsgame.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class Game {

    public Game() {
        this.startDate = new Date();
        this.playerWon = 0;
        this.computerWon = 0;
        this.draw = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private int gameNumber;
    private int gameLength;
    private String gameType; // M: Manual, A: Auto

    private int playerWon;
    private int computerWon;
    private int draw;

    private Date startDate;
    private Date endDate;

}
