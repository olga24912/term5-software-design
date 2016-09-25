package ru.spbau.mit;

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
