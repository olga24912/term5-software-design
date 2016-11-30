import org.junit.Test;
import ru.spbau.mit.Environment;
import ru.spbau.mit.Lexer;
import ru.spbau.mit.ParsingException;
import ru.spbau.mit.Token;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestEnvironment {
    @Test
    public void TestSimple() {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = null;
        try {
            tokens = lexer.parseString("echo $abc");
        } catch (ParsingException e) {
            assertFalse(true);
        }
        Environment environment = new Environment();
        assertEquals("echo ", environment.substituteVariable(tokens));
    }

    @Test
    public void TestSimpleWithSet() {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = null;
        try {
            tokens = lexer.parseString("echo $abc");
        } catch (ParsingException e) {
            assertFalse(true);
        }
        Environment environment = new Environment();
        environment.setVariable("abc", "val");
        assertEquals("echo val", environment.substituteVariable(tokens));
    }
}
