import model.Character;
import model.GameState;
import model.Map;

import java.io.IOException;

/**
 * Created by olga on 10.12.16.
 */
public class Controller {
    private View view;
    private Map map;
    Character character;

    private GameState gameState = GameState.Main;

    Controller() {
        view = new View(this);
    }

    public void start() {
        map = new Map(15, 15);
        character = new Character(map.getRandEmptyCell());
        map.addObject(character);
        map.setVisibility(character.getX(), character.getY());

        view.drawMap(map.getMapToPresent());
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
                    gameState = GameState.GameOver;
                }

                if (character.getTools().size() == 2) {
                    gameState = GameState.Win;
                }
                break;
            case Help:
                if (c == 'c') {
                    gameState = GameState.Main;
                }
                break;
            case ToolsView:
                if (c == 'c') {
                    gameState = GameState.Main;
                }
                break;
        }

        switch (gameState) {
            case Main:
                view.drawMap(map.getMapToPresent());
                break;
            case GameOver:
                view.drawGameOver();
                break;
            case Help:
                view.drawHelp();
                break;
            case ToolsView:
                view.drawTools(character);
                break;
            case Win:
                view.drawWin();
        }
    }
}
