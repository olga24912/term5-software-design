package ru.spbau.mit.model.game_objects;

import ru.spbau.mit.model.Point;
import ru.spbau.mit.model.map.Map;

import java.util.Random;

//класс для воюющего человечка
public class Mob extends GameObject {
    private Random random;
    private static final int[] dx = {0, 0, 1, -1};
    private static final int[] dy = {1, -1, 0, 0};

    private boolean isAlive = true;

    public Mob(Point pt) {
        super(pt);
        random = new Random();
    }

    @Override
    public void doTerm(Map map) {
        Character character = map.getCharacter();

        if ((character.getX() - pt.getX())*(character.getX() - pt.getX()) +
                        (character.getY() - pt.getY())*(character.getY() - pt.getY()) <= 1) {
                    map.shoot(this, character.getX(), character.getY());
                    return;
        }

        int term = (random.nextInt()%4 + 4) % 4;
        Point npt = new Point(pt.getX() + dx[term], pt.getY() + dy[term]);

        if (map.isEmpty(npt.getX(), npt.getY())) {
            pt = npt;
        }
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void shootReaction(GameObject killer) {
        isAlive = false;
    }

    @Override
    public char getSymbol() {
        return 'M';
    }
}
