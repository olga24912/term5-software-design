package model.map;

import model.game_objects.SimpleCell;

public interface MapGenerator {
    void generateMap(SimpleCell[][] simpleMap);
}
