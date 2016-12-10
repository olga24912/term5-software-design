package model;

import java.util.ArrayList;
import java.util.Random;

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
        ArrayList<GameObject> objects = map.getObjects();

        for (int i = 0; i < objects.size(); ++i) {
            if (objects.get(i).getSymbol() == 'I') {
                if ((objects.get(i).getX() - pt.getX())*(objects.get(i).getX() - pt.getX()) +
                        (objects.get(i).getY() - pt.getY())*(objects.get(i).getY() - pt.getY()) <= 1) {
                    map.shoot(this, objects.get(i).getX(), objects.get(i).getY());
                    return;
                }
            }
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
