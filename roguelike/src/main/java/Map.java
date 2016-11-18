import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    private Random rnd = new Random();

    private SimpleCell[][] simpleMap;
    private ArrayList <GameObject> objects = new ArrayList<>();
    private boolean[][] visibility;

    public Map(int n, int m) {
        simpleMap = new SimpleCell[n][m];
        visibility = new boolean[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                simpleMap[i][j] = new SimpleCell(new Point(i, j), CellType.Empty);
                visibility[i][j] = false;
            }
        }

        for (int i = 0; i < n; ++i) {
            simpleMap[i][0] = new SimpleCell(new Point(i, 0), CellType.WallV);
            simpleMap[i][m - 1] = new SimpleCell(new Point(i, m - 1), CellType.WallV);
        }

        for (int i = 0; i < m; ++i) {
            simpleMap[0][i] = new SimpleCell(new Point(0, i), CellType.WallG);
            simpleMap[n - 1][i] = new SimpleCell(new Point(n - 1, i), CellType.WallG);
        }
    }

    public gameState doTerm() throws IOException {
        for (GameObject object : objects) {
            object.doTerm(this);
        }

        draw();

        return null;
    }

    public void draw() throws IOException {

        GameObject[][] map = new GameObject[simpleMap.length][simpleMap[0].length];
        for (int i = 0; i < simpleMap.length; ++i) {
            System.arraycopy(simpleMap[i], 0, map[i], 0, simpleMap[i].length);
        }

        for (GameObject object : objects) {
            map[object.getX()][object.getY()] = object;
        }

        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (visibility[i][j]) {
                    System.out.print(map[i][j].getSymbol());
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    public void setVisibility(int x, int y) {
        if (x >= 0 && y >= 0 && x < simpleMap.length  && y < simpleMap[0].length) {
            visibility[x][y] = true;
        }
    }

    public Point getRandEmptyCell() {
        return new Point(1 + rnd.nextInt()%8, 1 + rnd.nextInt()%8);
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

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
}
