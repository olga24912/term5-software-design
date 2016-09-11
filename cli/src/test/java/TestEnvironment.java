import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestEnvironment {
    @Test
    public void TestSimple() {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.parseString("echo $abc");
        Environment environment = new Environment();
        assertEquals("echo ", environment.substituteVariable(tokens));
    }

    @Test
    public void TestSimpleWithSet() {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.parseString("echo $abc");
        Environment environment = new Environment();
        environment.setVariable("abc", "val");
        assertEquals("echo val", environment.substituteVariable(tokens));
    }
}
