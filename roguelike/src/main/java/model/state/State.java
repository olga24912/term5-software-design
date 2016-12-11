package model.state;

import model.map.Map;
import model.game_objects.Character;
import model.map.bfsMapGenerator;

import java.io.IOException;

import static model.state.GameState.GameOver;
import static model.state.GameState.Main;

public class State {
    private Map map;
    private Character character;

    private GameState gameState = GameState.Main;

    public State() {
        map = new Map(15, 15, new bfsMapGenerator());
        character = new Character(map.getRandEmptyCell());
        map.addObject(character);
        map.setVisibility(character.getX(), character.getY());
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
                character.setMove(c);
                try {
                    map.doTerm();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!character.isAlive()) {
                    gameState = GameOver;
                }

                if (character.getTools().size() == 2) {
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
        return character;
    }
}
