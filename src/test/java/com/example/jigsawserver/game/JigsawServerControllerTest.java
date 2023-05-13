package com.example.jigsawserver.game;

import com.example.jigsawserver.figures.Figure;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

class JigsawServerControllerTest {
    @Test
    void shouldAddPlayer() {
        // given
        var controller = new JigsawServerController();

        // when
        var response = controller.addPlayer();

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnMethodNotAllowed() throws NoSuchFieldException, IllegalAccessException {
        // given
        var controller = new JigsawServerController();
        Field gameStatus = JigsawServerController.class.getDeclaredField("gameStatus");
        gameStatus.setAccessible(true);
        gameStatus.set(controller, GameStatus.FINISHED);

        // when
        var response = controller.addPlayer();

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    void cannotStartGame() {
        // given
        var controller = new JigsawServerController();

        // when
        var response = controller.canStartGame();

        // then
        assertNotNull(response);
        assertEquals(false, response.getBody());

        // given
        controller.addPlayer();

        // when
        response = controller.canStartGame();

        // then
        assertNotNull(response);
        assertEquals(false, response.getBody());
    }

    @Test
    void canStartGame() {
        // given
        var controller = new JigsawServerController();
        controller.addPlayer();
        controller.addPlayer();

        // when
        var response = controller.canStartGame();

        // then
        assertNotNull(response);
        assertEquals(true, response.getBody());
    }

    @Test
    void stopGameWithPlayersLeft() throws NoSuchFieldException, IllegalAccessException {
        // given
        var controller = new JigsawServerController();
        var firstPlayer = controller.addPlayer().getBody();
        var secondPlayer = controller.addPlayer().getBody();
        Field offsetsField = JigsawServerController.class.getDeclaredField("offsets");
        offsetsField.setAccessible(true);

        // when
        var response = controller.stopGame(firstPlayer);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var offsets = (ConcurrentMap<String, Integer>) offsetsField.get(controller);
        assertFalse(offsets.containsKey(firstPlayer));
        assertTrue(offsets.containsKey(secondPlayer));
    }

    @Test
    void equalNextFigures() {
        // given
        var controller = new JigsawServerController();
        var firstPlayer = controller.addPlayer().getBody();
        var secondPlayer = controller.addPlayer().getBody();

        // when
        var firstFigure = controller.nextFigure(firstPlayer).getBody();
        var secondFigure = controller.nextFigure(secondPlayer).getBody();

        // then
        assertEquals(firstFigure, secondFigure);
    }

    @Test
    void playerNotFound() {
        // given
        var controller = new JigsawServerController();

        // when
        var response = controller.nextFigure("");

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void stopGameNoPlayers() throws NoSuchFieldException, IllegalAccessException {
        // given
        var controller = new JigsawServerController();
        var firstPlayer = controller.addPlayer().getBody();
        var secondPlayer = controller.addPlayer().getBody();
        Field offsetsField = JigsawServerController.class.getDeclaredField("offsets");
        offsetsField.setAccessible(true);
        Field gameStatus = JigsawServerController.class.getDeclaredField("gameStatus");
        gameStatus.setAccessible(true);
        Field sequence = JigsawServerController.class.getDeclaredField("sequence");
        sequence.setAccessible(true);
        controller.nextFigure(firstPlayer);

        // when
        var response = controller.stopGame(firstPlayer);
        response = controller.stopGame(secondPlayer);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var offsets = (ConcurrentMap<String, Integer>) offsetsField.get(controller);
        assertEquals(0, offsets.size());
        assertEquals(GameStatus.WAITING, (GameStatus) gameStatus.get(controller));
        assertEquals(0, ((ArrayList<Figure>) sequence.get(controller)).size());
    }
}
