import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestLexer {
    @Test
    public void testSimple() {
        Lexer lexer = new Lexer();
        ArrayList<Token> result = lexer.parseString("echo 1");
        ArrayList<Token> answer = new ArrayList<Token>();
        answer.add(new Token("echo", TokenType.TokenCommand));
        answer.add(new Token(" ", TokenType.TokenSpace));
        answer.add(new Token("1", TokenType.TokenText));
        assertEquals(answer, result);
    }

    @Test
    public void testPipe() {
        Lexer lexer = new Lexer();
        ArrayList<Token> result = lexer.parseString("echo 1 | cat");
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
        ArrayList<Token> result = lexer.parseString("echo '1 2 $asd 3'");
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
        ArrayList<Token> result = lexer.parseString("echo \"1 2 $asd 3 | cat |=\"");
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
        ArrayList<Token> result = lexer.parseString("var=$abs");
        ArrayList<Token> answer = new ArrayList<Token>();
        answer.add(new Token("var", TokenType.TokenVariableName));
        answer.add(new Token("=", TokenType.TokenAssign));
        answer.add(new Token("$abs", TokenType.TokenVariable));
        assertEquals(answer, result);
    }
}
