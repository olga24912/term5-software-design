package ru.spbau.mit.model.map;

import ru.spbau.mit.model.Point;
import ru.spbau.mit.model.game_objects.CellType;
import ru.spbau.mit.model.game_objects.Character;
import ru.spbau.mit.model.game_objects.GameObject;
import ru.spbau.mit.model.game_objects.SimpleCell;

import java.util.ArrayList;

//Class with game map and all game objects
public class Map {
    private SimpleCell[][] simpleMap;

    private Character character;
    private ArrayList <GameObject> objects = new ArrayList<GameObject>();
    private boolean[][] visibility;

    public Map(SimpleCell[][] simpleMap, ArrayList<GameObject> objects, Character character) {
        this.simpleMap = simpleMap;
        int n = simpleMap.length;
        int m = simpleMap[0].length;

        visibility = new boolean[n][m];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                visibility[i][j] = false;
            }
        }

        this.character = character;
        setVisibility(character.getX(), character.getY());
        this.objects = objects;
    }

    //All objects on map make a move.
    public void doTerm() {
        character.doTerm(this);
        for (GameObject object: objects) {
            object.doTerm(this);
        }
    }

    //set visibility cell for character
    public void setVisibility(int x, int y) {
        if (x >= 0 && y >= 0 && x < simpleMap.length  && y < simpleMap[0].length) {
            visibility[x][y] = true;
        }
    }

    //check if this cell is Empty
    public boolean isEmpty(int x, int y) {
        if (x >= 0 && x < simpleMap.length) {
            if (y >= 0 && y < simpleMap[0].length) {
                if (simpleMap[x][y].getType() == CellType.Empty || simpleMap[x][y].getType() == CellType.Road) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getXLen() {
        return simpleMap.length;
    }
    public int getYLen() {
        return simpleMap[0].length;
    }
    public CellType getCellType(int x, int y) {
        if (x >= 0 && x < simpleMap.length) {
            if (y >= 0 && y < simpleMap[0].length) {
                return simpleMap[x][y].getType();
            }
        }
        return CellType.WallV;
    }

    //Killer shoot cell(x, y). Other object need react on it
    public void shoot(GameObject killer, int x, int y) {
        if (character.getX() == x && character.getY() == y) {
            character.shootReaction(killer);
        }

        for (int i = 0; i < objects.size(); ++i) {
            if (objects.get(i).getX() == x && objects.get(i).getY() == y) {
                objects.get(i).shootReaction(killer);
                if (!objects.get(i).isAlive()) {
                    objects.remove(i);
                }
            }
        }
    }

    //Return all alive objects except character
    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    //Return map for drawing
    public GameObject[][] getMapToPresent() {
        GameObject[][] map = new GameObject[simpleMap.length][simpleMap[0].length];
        for (int i = 0; i < simpleMap.length; ++i) {
            System.arraycopy(simpleMap[i], 0, map[i], 0, simpleMap[i].length);
        }

        for (GameObject object : objects) {
            map[object.getX()][object.getY()] = object;
        }

        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (!visibility[i][j]) {
                    map[i][j]= new SimpleCell(new Point(i, j), CellType.Unknown);
                }
            }
        }

        map[character.getX()][character.getY()] = character;

        return map;
    }

    public Character getCharacter() {
        return character;
    }
}
