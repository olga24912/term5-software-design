package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    private Random rnd = new Random();
    private static final int roomsCount = 5;

    private SimpleCell[][] simpleMap;
    private ArrayList <GameObject> objects = new ArrayList<>();
    private boolean[][] visibility;

    public Map(int n, int m) {
        simpleMap = new SimpleCell[n][m];
        visibility = new boolean[n][m];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                visibility[i][j] = false;
            }
        }

        generateMap();
        objects.add(new Mob(getRandEmptyCell()));
        objects.add(new Tool(getRandEmptyCell()));
        objects.add(new Mob(getRandEmptyCell()));
        objects.add(new Tool(getRandEmptyCell()));
    }

    private void generateMap() {
        int n = simpleMap.length;
        int m = simpleMap[0].length;
        int[][] components =  new int[simpleMap.length][simpleMap[0].length];

        ArrayList<Integer> x1 = new ArrayList<>();
        ArrayList<Integer> y1 = new ArrayList<>();
        ArrayList<Integer> x2 = new ArrayList<>();
        ArrayList<Integer> y2 = new ArrayList<>();

        for (int i = 0; i < roomsCount; ++i) {
            x1.add(0);
            x2.add(0);
            y1.add(0);
            y2.add(0);
        }

        System.err.println(n + " " +  m);
        int cntLoopRepeat = 0;
        for (int i = 0; i < roomsCount; ++i) {
            System.err.println(i);
            x1.set(i, (rnd.nextInt() % (n - 3) + n - 3) % (n - 3));
            y1.set(i, (rnd.nextInt() % (m - 3) + m - 3) % (m - 3));
            x2.set(i, x1.get(i) + 2 + (rnd.nextInt() % (n - x1.get(i) - 2) + n - x1.get(i) - 2) % (n - x1.get(i) - 2));
            y2.set(i, y1.get(i) + 2 + (rnd.nextInt() % (n - y1.get(i) - 2) + n - y1.get(i) - 2) % (n - y1.get(i) - 2));

            boolean isBad = false;

            for (int j = 0; j < i; ++j) {
                if (!(x1.get(j) - 2 >= x2.get(i) ||
                      y1.get(j) - 2 >= y2.get(i) ||
                      x2.get(j) + 2 <= x1.get(i) ||
                      y2.get(j) + 2 <= y1.get(i))) {
                     isBad = true;
                }
            }

            if (isBad) {
                --i;
            }
            ++cntLoopRepeat;
            if (cntLoopRepeat >= roomsCount * 100) {
                cntLoopRepeat = 0;
                i = -1;
            }
        }

        for (int i = 0; i < roomsCount; ++i) {
            for (int x = x1.get(i); x <= x2.get(i); ++x) {
                for (int y = y1.get(i); y <= y2.get(i); ++y) {
                    components[x][y] = i + 2;
                }
            }
        }

        createPaths(components);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                System.err.print(components[i][j]);
            }
            System.err.println();
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (components[i][j] == 0) {
                    simpleMap[i][j] = new SimpleCell(new Point(i,j), CellType.WallG);
                    if (j > 0 && components[i][j - 1] != 0) {
                        simpleMap[i][j] = new SimpleCell(new Point(i,j), CellType.WallV);
                    }
                    if (j < m - 1 && components[i][j + 1] != 0) {
                        simpleMap[i][j] = new SimpleCell(new Point(i,j), CellType.WallV);
                    }
                } else if (components[i][j] == 1) {
                    simpleMap[i][j] = new SimpleCell(new Point(i,j), CellType.Road);
                } else {
                    simpleMap[i][j] = new SimpleCell(new Point(i, j), CellType.Empty);
                }
            }
        }
    }

    private void createPaths(int[][] components) {
        int n = components.length;
        int m = components[0].length;

        final int[] dx = {0, 0, -1, 1};
        final int[] dy = {1, -1, 0, 0};

        int[][] px = new int[n][m];
        int[][] py = new int[n][m];
        boolean[][] usedC = new boolean[n][m];
        boolean[] used = new boolean[roomsCount + 3];

        for (int i = 0; i < roomsCount + 3; ++i) {
            used[i] = false;
        }

        ArrayList<Point> queue = new ArrayList<Point>();
        int bg = 0;
        Point start = new Point(0, 0);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (components[i][j] == 2) {
                    start.setX(i);
                    start.setY(j);
                }
            }
        }

        queue.add(start);

        used[2] = true;

        while (bg < queue.size()) {
            int cx = queue.get(bg).getX();
            int cy = queue.get(bg).getY();
            ++bg;

            usedC[cx][cy] = true;
            int curColor =  components[cx][cy];
            if (curColor >= 2 && !used[curColor]) {
                used[curColor] = true;
                createOnePath(cx, cy, components, px, py);
            }

            for (int i = 0; i < 4; ++i) {
                Point point = new Point(cx + dx[i], cy + dy[i]);
                if (point.getX() >= 0 && point.getY() >= 0 &&
                        point.getX() < n && point.getY() < m) {
                    if (!usedC[point.getX()][point.getY()]) {
                        usedC[point.getX()][point.getY()] = true;
                        queue.add(point);
                        px[point.getX()][point.getY()] = cx;
                        py[point.getX()][point.getY()] = cy;
                    }
                }
            }
        }
    }

    private void createOnePath(int cx, int cy, int[][] components, int[][] px, int[][] py) {
        int color = components[cx][cy];
        int x = cx;
        int y = cy;
        while (components[x][y] == 0 || components[x][y] == color) {
            components[x][y] = 1;
            int nx = x, ny = y;
            nx = px[x][y];
            ny = py[x][y];
            x = nx;
            y = ny;
        }
        components[x][y] = 1;
    }

    public GameState doTerm() throws IOException {
        for (int i = objects.size() - 1; i >= 0; --i) {
            objects.get(i).doTerm(this);
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

    public void shoot(GameObject killer, int x, int y) {
        for (int i = 0; i < objects.size(); ++i) {
            if (objects.get(i).getX() == x && objects.get(i).getY() == y) {
                objects.get(i).shootReaction(killer);
                if (!objects.get(i).isAlive()) {
                    objects.remove(i);
                }
            }
        }
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

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

        return map;
    }
}
