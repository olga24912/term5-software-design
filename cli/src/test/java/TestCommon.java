import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
            assertFalse(true);
        }

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(tempFile.getCanonicalPath());
        } catch (IOException e) {
            assertFalse(true);
        }

        writer.println("The first line");
        writer.println("The second line");
        writer.close();
    }

    @Test
    public void testEcho() {
        command = "echo 1 2 3";
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);

        if (executionResult.getStdout() != null) {
            String result;
            Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
            result = scanner.next();
            assertEquals("1 2 3\n", result);
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testExit() {
        command = "exit";
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);
        assert(executionResult.isFinishFlag());
    }

    @Test
    public void testAssignment() {
        command = "a=b";
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);
        assertEquals(environment.getVariable("a"), "b");
    }

    @Test
    public void testWc() {
        try {
            command = "wc " + tempFile.getCanonicalPath();
        } catch (IOException e) {
            assertFalse(true);
        }
        ExecutionResult executionResult = null;
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);

        if (executionResult.getStdout() != null) {
            String result;
            Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
            result = scanner.next();
            assertEquals("3 6 31\n", result);
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testCat() {
        try {
            command = "cat " + tempFile.getCanonicalPath();
        } catch (IOException e) {
            assertFalse(true);
        }
        ExecutionResult executionResult = null;
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);

        if (executionResult.getStdout() != null) {
            String result;
            Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
            result = scanner.next();
            assertEquals("The first line\nThe second line\n", result);
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testPwd() {
        command = "pwd";
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);

        if (executionResult.getStdout() != null) {
            String result;
            Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
            result = scanner.next();
            try {
                assertEquals(new File( "." ).getCanonicalPath() + '\n', result);
            } catch (IOException e) {
                assertFalse(true);
            }
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testPipe() {
        command = "echo 1 | wc";
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);

        if (executionResult.getStdout() != null) {
            String result;
            Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
            result = scanner.next();
            assertEquals("1 1 2\n", result);
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testPipeCat() {
        command = "echo \" 3432 \" | cat";
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);

        if (executionResult.getStdout() != null) {
            String result;
            Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
            result = scanner.next();
            assertEquals(" 3432 \n", result);
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testVariable() {
        command = "echo $a";
        try {
            executionResult = Main.processOneLine(command, lexer, environment, parser);
        } catch (ParsingException | IOException e) {
            assertFalse(true);
        }

        assertFalse(executionResult == null);

        if (executionResult.getStdout() != null) {
            String result;
            Scanner scanner = new Scanner(executionResult.getStdout()).useDelimiter("\\A");
            result = scanner.next();
            assertEquals("\n", result);
        } else {
            assertFalse(true);
        }
    }
}
