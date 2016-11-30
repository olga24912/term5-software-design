import java.io.IOException;

/**
 * Created by olga on 18.11.16.
 */
public class State {
    Map map;
    Character character;

    public State() {
        map = new Map(15, 15);
        Point pt = map.getRandEmptyCell();
        System.err.println(pt.getX() + " " + pt.getY());
        character = new Character(pt);
        map.addObject(character);
        map.setVisibility(character.getX(), character.getY());
    }

    public void setMove(char c) {
        character.setMove(c);
    }

    public void draw() throws IOException {
        map.draw();
    }

    public void doTerm() throws IOException {
        map.doTerm();
    }
}
