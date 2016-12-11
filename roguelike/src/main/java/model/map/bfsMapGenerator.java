package model.map;

import model.Point;
import model.game_objects.CellType;
import model.game_objects.SimpleCell;

import java.util.ArrayList;
import java.util.Random;

public class bfsMapGenerator implements MapGenerator {
    private static final int roomsCount = 5;
    private Random rnd = new Random();

    @Override
    public void generateMap(SimpleCell[][] simpleMap) {
        int n = simpleMap.length;
        int m = simpleMap[0].length;
        int[][] components =  new int[simpleMap.length][simpleMap[0].length];

        ArrayList<Integer> x1 = new ArrayList<Integer>();
        ArrayList<Integer> y1 = new ArrayList<Integer>();
        ArrayList<Integer> x2 = new ArrayList<Integer>();
        ArrayList<Integer> y2 = new ArrayList<Integer>();

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

}
