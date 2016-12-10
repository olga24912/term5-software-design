import model.GameObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by olga on 10.12.16.
 */
public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void drawMap(GameObject[][] map) {
        for (GameObject[] aMap : map) {
            for (GameObject anAMap : aMap) {
                System.out.print(anAMap.getSymbol());
            }
            System.out.print('\n');
        }
    }

    public void waitInput() {
        try {
            char c = (char)(new BufferedReader(new InputStreamReader(System.in))).read();
            controller.makeMove(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
