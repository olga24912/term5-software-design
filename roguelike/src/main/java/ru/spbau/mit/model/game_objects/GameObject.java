package ru.spbau.mit.model.game_objects;

import ru.spbau.mit.model.map.Map;
import ru.spbau.mit.model.Point;

//All objects, that can be on map.
public abstract class GameObject {
    protected Point pt;

    public GameObject(Point pt) {
        this.pt = pt;
    }

    //Symbol for display this object
    public abstract char getSymbol();

    //For doing something on objects term.
    public void doTerm(Map map) {}

    //Check is object alive or not
    public boolean isAlive() {
        return true;
    }

    //Reaction if someone make some action on you.
    public void shootReaction(GameObject killer) {}

    public int getX() {
        return pt.getX();
    }

    public int getY() {
        return pt.getY();
    }
}
