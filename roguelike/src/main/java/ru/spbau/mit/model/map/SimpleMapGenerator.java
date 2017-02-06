package ru.spbau.mit.model.map;

import ru.spbau.mit.model.Point;
import ru.spbau.mit.model.game_objects.CellType;
import ru.spbau.mit.model.game_objects.SimpleCell;

public class SimpleMapGenerator implements MapGenerator {
    @Override
    public void generateMap(SimpleCell[][] simpleMap) {
        for(int i = 0; i < simpleMap.length; ++i) {
            for (int j = 0; j < simpleMap[0].length; ++j) {
                simpleMap[i][j] = new SimpleCell(new Point(i, j), CellType.Empty);
            }
        }

        for (int i = 0; i < simpleMap.length; ++i) {
            simpleMap[i][0] = new SimpleCell(new Point(i, 0), CellType.WallV);
            simpleMap[i][simpleMap[0].length - 1] = new SimpleCell(new Point(i, simpleMap[0].length - 1), CellType.WallV);
        }

        for (int i = 0; i < simpleMap[0].length; ++i) {
            simpleMap[0][i] = new SimpleCell(new Point(0, i), CellType.WallG);
            simpleMap[simpleMap.length - 1][i] = new SimpleCell(new Point(simpleMap.length - 1, i), CellType.WallG);
        }
    }
}
