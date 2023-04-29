package com.example.jigsawserver.game;

import com.example.jigsawserver.figures.Figure;
import com.example.jigsawserver.figures.FigureType;
import com.example.jigsawserver.figures.Rotation;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
public class JigsawServerController {
    private static final ConcurrentMap<String, Integer> offsets = new ConcurrentHashMap<>();
    private static final ArrayList<Figure> sequence = new ArrayList<>();
    private static final Random random = new Random();
    private static Timer timer;
    private static GameStatus gameStatus = GameStatus.WAITING;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/jigsaw");
    }

    @GetMapping("/new-game")
    public ResponseEntity<String> addPlayer() {
        UUID uuid = UUID.randomUUID();
        synchronized (offsets) {
            if (gameStatus == GameStatus.FINISHED) {
                return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
            }
            offsets.put(uuid.toString(), 0);
        }
        return new ResponseEntity<>(uuid.toString(), HttpStatus.OK);
    }
    
    @GetMapping("/can-start")
    public ResponseEntity<Boolean> canStartGame() {
        return new ResponseEntity<>(offsets.size() >= 2, HttpStatus.OK);
    }

    @GetMapping("/figure")
    public ResponseEntity<Figure> nextFigure(@RequestParam(name = "code") String code) {
        if (!offsets.containsKey(code) && (gameStatus != GameStatus.FINISHED)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (offsets.size() < 2) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } else if (gameStatus == GameStatus.WAITING) {
            gameStatus = GameStatus.PLAYING;
            setTimer();
        } else if (gameStatus == GameStatus.FINISHED) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        Integer oldOffset = offsets.get(code);
        if (oldOffset == sequence.size()) {
            sequence.add(generateFigure());
        }
        offsets.computeIfPresent(code, (k, v) -> v + 1);
        return new ResponseEntity<>(sequence.get(oldOffset), HttpStatus.OK);
    }

    @PostMapping("/stop")
    public void stopGame(@RequestParam(name = "code") String code) {
        offsets.remove(code);
        if (offsets.isEmpty()) {
            timer.cancel();
            sequence.clear();
            gameStatus = GameStatus.WAITING;
        }
    }

    private void setTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameStatus = GameStatus.FINISHED;
                offsets.forEach((k, v) -> stopGame(k));
                System.out.println("game finished");
            }
        }, 3*1000*60);
    }

    private Figure generateFigure() {
        var figureTypes = FigureType.values();
        var figureType = figureTypes[random.nextInt(figureTypes.length - 1)];
        var rotations = Rotation.values();
        var rotation = rotations[random.nextInt(rotations.length)];
        return new Figure(figureType, rotation);
    }
}
