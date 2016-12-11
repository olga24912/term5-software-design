import model.Point;
import model.game_objects.Mob;
import model.state.GameState;
import model.state.State;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestState {
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
}
