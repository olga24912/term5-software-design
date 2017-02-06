import org.junit.Test;
import ru.spbau.mit.Lexer;
import ru.spbau.mit.ParsingException;
import ru.spbau.mit.Token;
import ru.spbau.mit.TokenType;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestLexer {
    @Test
    public void testSimple() {
        Lexer lexer = new Lexer();
        ArrayList<Token> result = null;
        try {
            result = lexer.parseString("echo 1");
        } catch (ParsingException e) {
            assertFalse(true);
        }
        ArrayList<Token> answer = new ArrayList<Token>();
        answer.add(new Token("echo", TokenType.TokenCommand));
        answer.add(new Token(" ", TokenType.TokenSpace));
        answer.add(new Token("1", TokenType.TokenText));
        assertEquals(answer, result);
    }

    @Test
    public void testPipe() {
        Lexer lexer = new Lexer();
        ArrayList<Token> result = null;
        try {
            result = lexer.parseString("echo 1 | cat");
        } catch (ParsingException e) {
            assertFalse(true);
        }
        ArrayList<Token> answer = new ArrayList<Token>();
        answer.add(new Token("echo", TokenType.TokenCommand));
        answer.add(new Token(" ", TokenType.TokenSpace));
        answer.add(new Token("1", TokenType.TokenText));
        answer.add(new Token(" ", TokenType.TokenSpace));
        answer.add(new Token("|", TokenType.TokenPipe));
        answer.add(new Token(" ", TokenType.TokenSpace));
        answer.add(new Token("cat", TokenType.TokenCommand));
        assertEquals(answer, result);
    }

    @Test
    public void testQuote() {
        Lexer lexer = new Lexer();
        ArrayList<Token> result = null;
        try {
            result = lexer.parseString("echo '1 2 $asd 3'");
        } catch (ParsingException e) {
            assertFalse(true);
        }
        ArrayList<Token> answer = new ArrayList<Token>();
        answer.add(new Token("echo", TokenType.TokenCommand));
        answer.add(new Token(" ", TokenType.TokenSpace));
        answer.add(new Token("'", TokenType.TokenQuote));
        answer.add(new Token("1 2 $asd 3", TokenType.TokenText));
        answer.add(new Token("'", TokenType.TokenQuote));
        assertEquals(answer, result);
    }

    @Test
    public void testDoubleQuote() {
        Lexer lexer = new Lexer();
        ArrayList<Token> result = null;
        try {
            result = lexer.parseString("echo \"1 2 $asd 3 | cat |=\"");
        } catch (ParsingException e) {
            assertFalse(true);
        }
        ArrayList<Token> answer = new ArrayList<Token>();
        answer.add(new Token("echo", TokenType.TokenCommand));
        answer.add(new Token(" ", TokenType.TokenSpace));
        answer.add(new Token("\"", TokenType.TokenDoubleQuote));
        answer.add(new Token("1 2 ", TokenType.TokenText));
        answer.add(new Token("$asd", TokenType.TokenVariable));
        answer.add(new Token(" 3 | cat |=", TokenType.TokenText));
        answer.add(new Token("\"", TokenType.TokenDoubleQuote));
        assertEquals(answer, result);
    }

    @Test
    public void testAssign() {
        Lexer lexer = new Lexer();
        ArrayList<Token> result = null;
        try {
            result = lexer.parseString("var=$abs");
        } catch (ParsingException e) {
            assertFalse(true);
        }
        ArrayList<Token> answer = new ArrayList<Token>();
        answer.add(new Token("var", TokenType.TokenVariableName));
        answer.add(new Token("=", TokenType.TokenAssign));
        answer.add(new Token("$abs", TokenType.TokenVariable));
        assertEquals(answer, result);
    }
}
