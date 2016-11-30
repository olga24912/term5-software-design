public class Character extends GameObject{
    int moveX;
    int moveY;
    Point shoot = new Point(0, 0);
    private static final int[] dx = {0, 0, 1, -1};
    private static final int[] dy = {1, -1, 0, 0};

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
        int[][] used = new int[map.getXLen()][map.getYLen()];
        for (int i = 0; i < used.length; ++i) {
            for (int j = 0; j < used[0].length; ++j) {
                used[i][j] = 0;
            }
        }
        setVisibility(map, nx, ny, used);
        if (map.isEmpty(nx, ny)) {
            pt.setX(nx);
            pt.setY(ny);
        }

        map.shoot(pt.getX(), pt.getY());
    }

    private void setVisibility(Map map, int x, int y, int[][] used) {
        if (x < 0 || y < 0 || x >= used.length || y >= used[0].length || used[x][y] == 1) {
            return;
        }
        used[x][y] = 1;
        map.setVisibility(x, y);
        if (map.getCellType(x, y) != CellType.Empty) {
            return;
        }
        for (int i = 0; i < 4; ++i) {
            setVisibility(map, x + dx[i], y + dy[i], used);
        }
    }

    public void setMove(char c) {
        moveX = 0;
        moveY = 0;
        shoot.setX(0);
        shoot.setY(0);
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
        } else if (c == 'i') {
            shoot.setX(pt.getX() + 1);
            shoot.setY(pt.getY());
        } else if (c == 'k') {
            shoot.setX(pt.getX() - 1);
            shoot.setY(pt.getY());
        } else if (c == 'j') {
            shoot.setX(pt.getX());
            shoot.setY(pt.getY() - 1);
        } else if (c == 'l') {
            shoot.setX(pt.getX());
            shoot.setY(pt.getY() + 1);
        }
    }
}