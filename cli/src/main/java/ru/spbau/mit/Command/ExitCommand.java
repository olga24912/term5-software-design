package ru.spbau.mit.Command;

import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Environment;
import ru.spbau.mit.ExecutionResult;

import java.io.InputStream;

/**
 * Class for 'exit' command
 *
 * ignore inputStream,
 * put the finishFlag = true in ExecutionResult
 * inputStream = null in ExecutionResult
 */
public class ExitCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        ExecutionResult executionResult = new ExecutionResult();
        executionResult.setFinishFlag(true);
        return executionResult;
    }
}
