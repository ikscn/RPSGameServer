package com.jwrks.rpsgame.controller;

import com.jwrks.rpsgame.model.Weapon;
import com.jwrks.rpsgame.service.GameService;
import com.jwrks.rpsgame.model.Game;
import com.jwrks.rpsgame.model.Round;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/weapons")
    public List<Weapon> getWeapons() {
        List<Weapon> weapons = gameService.getWeapons();
        log.info("Available weapons: {}", weapons);
        return weapons;
    }

    @GetMapping("/play/round")
    public Round playRound(int playerWeapon) {
        log.info("Player selected: {}", playerWeapon);
        Round result = gameService.playSingleRound(playerWeapon);
        log.info("Result: {}", result);
        return result;
    }

    @GetMapping("/play/auto")
    public Game playAuto(int playerWeapon, int gameLength) {
        Game game = gameService.playAuto(playerWeapon, gameLength);
        log.info("Result: {}", game.toString());
        return game;
    }

    @PostMapping("/save/manual")
    public ResponseEntity<Object> saveManualGame(@RequestBody Game game) {
        log.info("Manual Game Result to Save: {}", game.toString());
        Game savedGame = gameService.saveManualGame(game);
        return generateResponse("Game successfully saved.", HttpStatus.OK, savedGame);
    }

    @GetMapping("/history")
    public List<Game> getGameHistory() {
        return gameService.getGameHistory();
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        return new ResponseEntity<Object>(map, status);
    }

}
