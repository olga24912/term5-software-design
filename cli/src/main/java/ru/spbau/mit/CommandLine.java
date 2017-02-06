package ru.spbau.mit;

import java.io.IOException;
import java.io.InputStream;

/**
 * interface for all type of command include pipe and assign.
 */
public interface CommandLine {
    /**
     * Run the command
     *
     * @param environment info about variable in fact use only in assignment
     * @param stdin InputStream
     * @return ExecutionResult: (finishFlag, inputStream). Put to inputStream result of execute
     * @throws IOException
     */
    ExecutionResult execute(Environment environment,InputStream stdin) throws IOException;
}