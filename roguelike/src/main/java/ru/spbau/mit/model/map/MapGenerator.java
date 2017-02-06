package ru.spbau.mit.model.map;

import ru.spbau.mit.model.game_objects.SimpleCell;

//class for simple map generation
public interface MapGenerator {
    void generateMap(SimpleCell[][] simpleMap);
}
