package ru.spbau.mit.model.map;

import ru.spbau.mit.model.game_objects.SimpleCell;

public interface MapGenerator {
    void generateMap(SimpleCell[][] simpleMap);
}
