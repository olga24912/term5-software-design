package ru.spbau.mit.Command;

import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Environment;
import ru.spbau.mit.ExecutionResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Class for 'exit' command
 *
 * ignore inputStream,
 * write to inputStream in ExecutionResult path to current directory
 */
public class PwdCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        ExecutionResult executionResult = new ExecutionResult();
        String current;
        current = new java.io.File( "." ).getCanonicalPath() + '\n';
        InputStream outputStream = new ByteArrayInputStream(current.getBytes(StandardCharsets.UTF_8));
        executionResult.setStdout(outputStream);
        return executionResult;
    }
}
