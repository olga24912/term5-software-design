import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TestCommon {
    private Lexer lexer;
    private Environment environment;
    private Parser parser;
    private String command;
    private ExecutionResult executionResult;
    private File tempFile;

    @Before
    public void setUp() {
        lexer = new Lexer();
        environment = new Environment();
        parser = new Parser();
        command = "";
        executionResult = null;

        tempFile = null;

        try {
            tempFile = File.createTempFile("tmp", ".tmp");
        } catch (IOException e) {
            fail();
        }

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(tempFile.getCanonicalPath());
        } catch (IOException e) {
            fail();
        }

        writer.println("The first line");
        writer.println("The second line");
        writer.close();
    }

    @Test
    public void testEcho() throws ParsingException, IOException {
        command = "echo 1 2 3";
        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());

        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals("1 2 3\n", result);
    }

    @Test
    public void testExit() throws ParsingException, IOException {
        command = "exit";
        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assert(executionResult.isFinishFlag());
    }

    @Test
    public void testAssignment() throws ParsingException, IOException {
        command = "a=b";
        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertEquals(environment.getVariable("a"), "b");
    }

    @Test
    public void testWc() throws IOException, ParsingException {
        command = "wc " + tempFile.getCanonicalPath();

        ExecutionResult executionResult = null;

        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());
        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals("3 6 31\n", result);
    }

    @Test
    public void testCat() throws IOException, ParsingException {
        command = "cat " + tempFile.getCanonicalPath();

        ExecutionResult executionResult = null;
        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());

        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals("The first line\nThe second line\n", result);
    }

    @Test
    public void testPwd() throws ParsingException, IOException {
        command = "pwd";
        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());

        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        try {
            assertEquals(new File(".").getCanonicalPath() + '\n', result);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testPipe() throws ParsingException, IOException {
        command = "echo 1 | wc";
        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());

        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals("1 1 2\n", result);
    }

    @Test
    public void testPipeCat() throws ParsingException, IOException {
        command = "echo \" 3432 \" | cat";
        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());

        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals(" 3432 \n", result);
    }

    @Test
    public void testVariable() throws ParsingException, IOException {
        command = "echo $a";
        executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());

        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals("\n", result);
    }

    @Test
    public void testGrep() throws IOException, ParsingException {
        command = "grep " + " \"f[a-z]{2,3}t\" " + tempFile.getCanonicalPath();

        ExecutionResult executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertFalse(executionResult == null);

        assertNotNull(executionResult.getStdout());
        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals("The first line\n", result);
    }

    @Test
    public void testGrepFlag() throws IOException, ParsingException {
        command = "grep " + " -w -i -A 2 \"F[a-z]{2,3}T\" " + tempFile.getCanonicalPath();

        ExecutionResult executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());
        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals("The first line\nThe second line\n", result);
    }

    @Test
    public void testGrepPipe() throws ParsingException, IOException {
        command = "echo \"sdfdfirstsdf\naaaa\nfirst\na\na\n\na\" | grep " + " -i -A 2 \"F[a-z]{2,3}T\" ";
        ExecutionResult executionResult = Main.processOneLine(command, lexer, environment, parser);

        assertNotNull(executionResult);
        assertNotNull(executionResult.getStdout());

        String result;
        Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
        result = scanner.next();
        assertEquals("sdfdfirstsdf\n" +
                "aaaa\n" +
                "first\n" +
                "a\n", result);
    }
}
