package ru.spbau.mit.model.game_objects;

import ru.spbau.mit.model.Point;

//обычная клетка, на которой ничего не стоит
public class SimpleCell extends GameObject {
    private CellType type;

    public SimpleCell(Point pt, CellType type) {
        super(pt);
        this.type = type;
    }

    @Override
    public char getSymbol() {
        if (type == CellType.WallG) {
            return '-';
        } else if (type == CellType.WallV) {
            return '|';
        } else if (type == CellType.Empty) {
            return '.';
        } else if (type == CellType.Road) {
            return '#';
        }
        return ' ';
    }

    public CellType getType() {
        return type;
    }
}
