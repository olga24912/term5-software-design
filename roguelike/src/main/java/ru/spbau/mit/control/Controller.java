package ru.spbau.mit.control;

import ru.spbau.mit.model.state.State;
import ru.spbau.mit.view.View;

//класс, отвечающий за состояние игры. Прослойка между моделью и отображением игры.
public class Controller {
    private View view;
    private State state;

    public Controller() {
        view = new View(this);
    }

    //начало новой игры
    public void start() {
        state = new State();
        view.drawMap(state.getMap().getMapToPresent());
    }

    //реагирует на нажатем пользователем клавишы.
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
