package ru.spbau.mit.Command;

import ru.spbau.mit.Environment;
import ru.spbau.mit.ExecutionResult;

import java.io.IOException;
import java.io.InputStream;

/**
 * class for pipe |
 * Have left part of command and right part
 */
public class PipeCommand extends Statement {
    Statement left;
    Statement right;

    public PipeCommand(Statement left, Statement right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Execute two part use output of left statement for
     * right statement.
     *
     * @param environment info about variable in fact use only in assignment
     * @param stdin InputStream
     * @return composition of executing two statements
     * @throws IOException
     */
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        ExecutionResult executionResult = left.execute(environment, stdin);
        if (executionResult.isFinishFlag()) {
            return executionResult;
        }
        return right.execute(environment, executionResult.getStdout());
    }
}
