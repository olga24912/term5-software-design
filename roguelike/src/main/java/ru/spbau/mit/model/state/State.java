package ru.spbau.mit.model.state;

import ru.spbau.mit.model.game_objects.Character;
import ru.spbau.mit.model.map.BfsMapGenerator;
import ru.spbau.mit.model.map.Map;
import ru.spbau.mit.model.map.MapBuilder;

import java.io.IOException;

import static ru.spbau.mit.model.state.GameState.GameOver;
import static ru.spbau.mit.model.state.GameState.Main;

public class State {
    private Map map;

    private GameState gameState = GameState.Main;

    public State() {
        map = (new MapBuilder()).setGenerator(new BfsMapGenerator()).build();
    }

    public State(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void makeMove(char c) {
        switch (gameState) {
            case GameOver:
                break;
            case Main:
                if (c == 'h') {
                    gameState = GameState.Help;
                    break;
                } else if (c == 't') {
                    gameState = GameState.ToolsView;
                }
                map.getCharacter().setMove(c);
                try {
                    map.doTerm();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!map.getCharacter().isAlive()) {
                    gameState = GameOver;
                }

                if (map.getObjects().size() == 0) {
                    gameState = GameState.Win;
                }
                break;
            case Help:
                if (c == 'c') {
                    gameState = Main;
                }
                break;
            case ToolsView:
                if (c == 'c') {
                    gameState = Main;
                }
                break;
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public Character getCharacter() {
        return map.getCharacter();
    }
}
