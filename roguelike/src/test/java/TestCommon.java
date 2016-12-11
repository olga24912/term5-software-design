import model.Point;
import model.game_objects.Character;
import model.game_objects.Mob;
import model.map.MapBuilder;
import model.map.Map;
import model.state.GameState;
import model.state.State;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCommon {
    @Test
    public void testChangeState() {
        State state = new State();

        assertEquals(state.getGameState(), GameState.Main);
        state.makeMove('h');
        assertEquals(state.getGameState(), GameState.Help);
        state.makeMove('c');
        assertEquals(state.getGameState(), GameState.Main);
        state.makeMove('t');
        assertEquals(state.getGameState(), GameState.ToolsView);
        state.makeMove('c');
        assertEquals(state.getGameState(), GameState.Main);
        state.getCharacter().shootReaction(new Mob(new Point(0, 0)));
        state.makeMove('w');
        assertEquals(state.getGameState(), GameState.GameOver);
    }

    @Test
    public void testCharacterMove() {
        Map map = (new MapBuilder()).setCharacter((new Character(new Point(5, 5)))).setMobsCount(0).build();

        State state = new State(map);

        state.makeMove('a');
        assertEquals(state.getCharacter().getX(), 5);
        assertEquals(state.getCharacter().getY(), 4);

        state.makeMove('d');
        assertEquals(state.getCharacter().getX(), 5);
        assertEquals(state.getCharacter().getY(), 5);

        state.makeMove('w');
        assertEquals(state.getCharacter().getX(), 4);
        assertEquals(state.getCharacter().getY(), 5);

        state.makeMove('s');
        assertEquals(state.getCharacter().getX(), 5);
        assertEquals(state.getCharacter().getY(), 5);

    }
}
