package com.jwrks.rpsgame;

import com.jwrks.rpsgame.model.Game;
import com.jwrks.rpsgame.model.Round;
import com.jwrks.rpsgame.model.Weapon;
import com.jwrks.rpsgame.repo.GameRepository;
import com.jwrks.rpsgame.repo.WeaponRepository;
import com.jwrks.rpsgame.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private WeaponRepository weaponRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Weapon> mockWeaponList = Arrays.asList(
                new Weapon(0L, "Rock", "rock.png"),
                new Weapon(1L, "Paper", "paper.png"),
                new Weapon(2L, "Scissors", "scissors.png")
        );

        when(weaponRepository.findAll()).thenReturn(mockWeaponList);

    }

    @Test
    void testPlaySingleRound() {
        Round round = gameService.playSingleRound(0);

        assertNotNull(round);
        assertNotNull(round.getPlayerWeapon());
        assertNotNull(round.getComputerWeapon());
    }

    @Test
    void testPlayAuto() {

        Game game = gameService.playAuto(0, 5);

        assertNotNull(game);
        assertEquals(5, game.getGameLength());
        assertEquals("A", game.getGameType());
        assertEquals(game.getGameLength(), game.getDraw() + game.getPlayerWon() + game.getComputerWon());
        assertNotNull(game.getEndDate());
    }

    @Test
    void testGetGameHistory() {
        when(gameRepository.findAll(any())).thenReturn(Arrays.asList(new Game(), new Game()));

        List<Game> gameHistory = gameService.getGameHistory();

        assertNotNull(gameHistory);
        assertEquals(2, gameHistory.size());
    }

    @Test
    void testSaveManualGame() {
        Game newGame = new Game();
        when(gameRepository.save(any())).thenReturn(newGame);

        Game savedGame = gameService.saveManualGame(newGame);

        assertNotNull(savedGame);
    }
}

