import model.Character;
import model.Map;

import java.io.IOException;

/**
 * Created by olga on 10.12.16.
 */
public class Controller {
    private View view;
    private Map map;
    Character character;

    Controller() {
        view = new View(this);
    }

    public void start() {
        map = new Map(15, 15);
        character = new Character(map.getRandEmptyCell());
        map.addObject(character);
        map.setVisibility(character.getX(), character.getY());

        view.drawMap(map.getMapToPresent());
        view.waitInput();
    }

    public void makeMove(char c) {
        character.setMove(c);
        try {
            map.doTerm();
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.drawMap(map.getMapToPresent());
        view.waitInput();
    }
}
