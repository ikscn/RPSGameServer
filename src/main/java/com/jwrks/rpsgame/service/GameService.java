package com.jwrks.rpsgame.service;

import com.jwrks.rpsgame.model.Game;
import com.jwrks.rpsgame.model.Round;
import com.jwrks.rpsgame.model.Weapon;
import com.jwrks.rpsgame.repo.GameRepository;
import com.jwrks.rpsgame.repo.WeaponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final WeaponRepository weaponRepository;

    private Game game;
    private List<Weapon> weapons;

    public List<Weapon> getWeapons() {
        if(this.weapons == null || this.weapons.isEmpty()) {
            this.weapons = (List<Weapon>) weaponRepository.findAll();
        }
        return this.weapons;
    }

    public void startAutoGame() {
        game = new Game();
    }

    // Play a single round
    public Round playSingleRound(int playerWeaponId) {

        // Select computer weapon randomly
        Random rand = new Random();
        int computerWeaponId = rand.nextInt(3);
        log.info("Random number for computer: {}", computerWeaponId);

        // Select player weapon randomly if it is not selected
        if(playerWeaponId == -1) {
            playerWeaponId = rand.nextInt(3);
        }

        // Define round weapons
        Weapon playerWeapon = getWeapon(playerWeaponId);
        Weapon computerWeapon = getWeapon(computerWeaponId);

        log.info("Player hits a {}", playerWeapon.getName());
        log.info("Computer hits a {}", computerWeapon.getName());

        Round round = play(playerWeapon, computerWeapon);
        log.info("Round: {}", round);

        return round;
    }

    // Decision for the result of the round
    private Round play(Weapon playerWeapon, Weapon computerWeapon) {
        Round round = new Round(playerWeapon, computerWeapon);
        if(playerWeapon.getId().intValue() == computerWeapon.getId().intValue()) {
            round.setResult(0);   // DRAW
        } else {
            switch (playerWeapon.getId().intValue()) {
                case 0 -> {
                    if (computerWeapon.getId().intValue() == 2) {
                        round.setResult(1);   // Player wins
                    } else {
                        round.setResult(2);   // Computer wins
                    }
                }
                case 1 -> {
                    if (computerWeapon.getId().intValue() == 2) {
                        round.setResult(2);   // Computer wins
                    } else {
                        round.setResult(1);   // Player wins
                    }
                }
                case 2 -> {
                    if (computerWeapon.getId().intValue() == 1) {
                        round.setResult(1);   // Player wins
                    } else {
                        round.setResult(2);  // Computer wins
                    }
                }
            }
        }
        return round;
    }

    // Play automatically for given game length and selected player weapon
    public Game playAuto(int playerWeapon, int gameLength) {
        startAutoGame();
        game.setGameNumber((int) gameRepository.count() + 1);
        game.setGameLength(gameLength);
        game.setGameType("A");
        for(int i=0; i<gameLength; i++) {
            int winner = playSingleRound(playerWeapon).getResult();
            switch (winner) {
                case 0 -> game.setDraw(game.getDraw() + 1);
                case 1 -> game.setPlayerWon(game.getPlayerWon() + 1);
                case 2 -> game.setComputerWon(game.getComputerWon() + 1);
                default -> {}
            }
        }
        endAutoGame();
        return game;
    }

    // End game and save the game result to DB
    public void endAutoGame() {
        game.setEndDate(new Date());
        gameRepository.save(game);
    }

    // Gets the history of the games
    public List<Game> getGameHistory() {
        return gameRepository.findAll(Sort.by(Sort.Direction.DESC,"gameNumber"));
    }

    public Game saveManualGame(Game game) {
        game.setGameNumber((int) gameRepository.count() + 1);
        Game savedGame = gameRepository.save(game);
        log.info("Game saved: " + savedGame);
        return savedGame;
    }

    private Weapon getWeapon(int id) {
        for(Weapon weapon :getWeapons()) {
            if(weapon.getId().intValue() == id) {
                return weapon;
            }
        }
        return new Weapon(-1L,"Random", "question.png");
    }

}
