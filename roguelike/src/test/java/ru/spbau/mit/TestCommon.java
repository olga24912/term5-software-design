package ru.spbau.mit;

import ru.spbau.mit.model.Point;
import ru.spbau.mit.model.game_objects.Character;
import ru.spbau.mit.model.game_objects.Mob;
import ru.spbau.mit.model.game_objects.Tool;
import ru.spbau.mit.model.map.Map;
import ru.spbau.mit.model.map.MapBuilder;
import ru.spbau.mit.model.state.GameState;
import ru.spbau.mit.model.state.State;
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

    @Test
    public void testTakeTools() {
        Map map = (new MapBuilder()).setCharacter((new Character(new Point(5, 5)))).
                setMobsCount(0).setToolsCount(0).addObject(new Tool(new Point(4, 5))).
                addObject(new Tool(new Point(4, 6))).
                addObject(new Tool(new Point(5, 6))).
                addObject(new Tool(new Point(5, 5))).build();

        State state = new State(map);

        state.makeMove('i');
        assertEquals(map.getCharacter().getTools().size(), 1);
        assertEquals(map.getObjects().size(),  3);

        state.makeMove('w');
        state.makeMove('l');
        assertEquals(map.getCharacter().getTools().size(), 2);
        assertEquals(map.getObjects().size(), 2);

        state.makeMove('d');
        state.makeMove('k');
        assertEquals(map.getCharacter().getTools().size(), 3);
        assertEquals(map.getObjects().size(), 1);

        state.makeMove('s');
        state.makeMove('j');
        assertEquals(map.getCharacter().getTools().size(), 4);
        assertEquals(map.getObjects().size(), 0);

        assertEquals(state.getGameState(), GameState.Win);
    }

    @Test
    public void testMobsKill() {
        Map map = (new MapBuilder()).setCharacter((new Character(new Point(5, 5)))).
                setMobsCount(0).setToolsCount(0).addObject(new Mob(new Point(4, 5))).
                build();

        State state = new State(map);

        assertEquals(map.getObjects().size(), 1);

        state.makeMove('i');
        assertEquals(map.getCharacter().getTools().size(), 0);
        assertEquals(map.getObjects().size(),  0);

        assertEquals(state.getGameState(), GameState.Win);
    }

    @Test
    public void testGameOver() {
        Map map = (new MapBuilder()).setCharacter((new Character(new Point(5, 5)))).
                setMobsCount(0).setToolsCount(0).addObject(new Mob(new Point(3, 5))).
                build();

        State state = new State(map);

        assertEquals(map.getObjects().size(), 1);

        state.makeMove('w');
        assertEquals(map.getCharacter().getTools().size(), 0);
        assertEquals(map.getObjects().size(),  1);

        assertEquals(state.getGameState(), GameState.GameOver);

    }
}
