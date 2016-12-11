package ru.spbau.mit.control;

import ru.spbau.mit.model.state.State;
import ru.spbau.mit.view.View;

//Middle class between view and model
public class Controller {
    private View view;
    private State state;

    public Controller() {
        view = new View(this);
    }

    //start new game
    public void start() {
        state = new State();
        view.drawMap(state.getMap().getMapToPresent());
    }

    //reaction on key press
    public void makeMove(char c) {
        state.makeMove(c);

        switch (state.getGameState()) {
            case Main:
                view.drawMap(state.getMap().getMapToPresent());
                break;
            case GameOver:
                view.drawGameOver();
                break;
            case Help:
                view.drawHelp();
                break;
            case ToolsView:
                view.drawTools(state.getCharacter());
                break;
            case Win:
                view.drawWin();
        }
    }
}
