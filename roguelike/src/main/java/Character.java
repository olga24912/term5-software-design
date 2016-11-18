public class Character extends GameObject{
    int moveX;
    int moveY;

    public Character(Point pt) {
        super(pt);
    }

    @Override
    public char getSymbol() {
        return 'I';
    }

    @Override
    public void doTerm(Map map) {
        int nx = pt.getX() + moveX, ny = pt.getY() + moveY;
        map.setVisibility(nx, ny);
        if (map.isEmpty(nx, ny)) {
            pt.setX(nx);
            pt.setY(ny);
        }
    }

    public void setMove(char c) {
        if (c == 'a') {
            moveX = 0;
            moveY = -1;
        } else if (c == 'w') {
            moveX = -1;
            moveY = 0;
        } else if (c == 's') {
            moveX = 1;
            moveY = 0;
        } else if (c == 'd') {
            moveX = 0;
            moveY = 1;
        }
    }
}
