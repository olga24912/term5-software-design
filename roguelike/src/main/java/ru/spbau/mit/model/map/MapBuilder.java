package ru.spbau.mit.model.map;

import ru.spbau.mit.model.Point;
import ru.spbau.mit.model.game_objects.*;
import ru.spbau.mit.model.game_objects.Character;

import java.util.ArrayList;
import java.util.Random;

public class MapBuilder {
    private Random rnd = new Random();

    private int n = 15;
    private int m = 15;
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private MapGenerator generator = new SimpleMapGenerator();
    private int mobsCount = 2;
    private int toolsCount = 2;
    private Character character = null;
    private SimpleCell[][] simpleMap;

    public MapBuilder() {
        genSimpleMap();
    }

    public MapBuilder setCharacter(Character character) {
        this.character = character;
        return this;
    }

    public Map build() {
        for (int i = 0; i < mobsCount; ++i) {
            objects.add(new Mob(getRandEmptyCell()));
        }

        for (int i = 0; i < toolsCount; ++i) {
            objects.add(new Tool(getRandEmptyCell()));
        }

        if (character == null) {
            character = new Character(getRandEmptyCell());
        }

        return new Map(simpleMap, objects, character);
    }

    public MapBuilder setN(int n) {
        this.n = n;
        genSimpleMap();
        return this;
    }

    public MapBuilder setM(int m) {
        this.m = m;
        genSimpleMap();

        return this;
    }

    public MapBuilder addObject(GameObject object) {
        objects.add(object);
        return this;
    }

    public MapBuilder setGenerator(MapGenerator generator) {
        this.generator = generator;
        genSimpleMap();

        return this;
    }

    public MapBuilder setMobsCount(int mobsCount) {
        this.mobsCount = mobsCount;

        return this;
    }

    public MapBuilder setToolsCount(int toolsCount) {
        this.toolsCount = toolsCount;

        return this;
    }

    private void genSimpleMap() {
        simpleMap = new SimpleCell[n][m];
        generator.generateMap(simpleMap);
    }

    public Point getRandEmptyCell() {
        int cntEmpty = 0;
        int n = simpleMap.length;
        int m = simpleMap[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (isEmpty(i, j)) {
                    ++cntEmpty;
                }
            }
        }

        int num = (rnd.nextInt()%cntEmpty + cntEmpty)%cntEmpty;

        Point res = new Point(0, 0);
        cntEmpty = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (isEmpty(i, j)) {
                    if (cntEmpty == num) {
                        res.setX(i);
                        res.setY(j);
                    }
                    cntEmpty++;
                }
            }
        }

        return res;
    }

    private boolean isEmpty(int x, int y) {
        if (x >= 0 && x < simpleMap.length) {
            if (y >= 0 && y < simpleMap[0].length) {
                if (simpleMap[x][y].getType() == CellType.Empty || simpleMap[x][y].getType() == CellType.Road) {
                    return true;
                }
            }
        }
        return false;
    }
}
