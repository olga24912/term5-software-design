package ru.spbau.mit.model.game_objects;

import ru.spbau.mit.model.map.Map;
import ru.spbau.mit.model.Point;

//Объекты, которые могут быть на карте
public abstract class GameObject {
    protected Point pt;

    public GameObject(Point pt) {
        this.pt = pt;
    }

    //символ, для отрисовки на карте
    public abstract char getSymbol();
    //элемент делает ход
    public void doTerm(Map map) {}

    //является ли этот персонаж еще живым.
    public boolean isAlive() {
        return true;
    }

    //Как-то отреагировать на то, что тебя кто-то ударил(например, умереть)
    public void shootReaction(GameObject killer) {}

    public int getX() {
        return pt.getX();
    }

    public int getY() {
        return pt.getY();
    }
}
