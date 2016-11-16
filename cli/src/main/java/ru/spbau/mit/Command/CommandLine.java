package ru.spbau.mit.Command;

import ru.spbau.mit.Environment;
import ru.spbau.mit.ExecutionResult;

import java.io.IOException;
import java.io.InputStream;

/** interface for all types of command, including pipe and assign */
public interface CommandLine {
    /**
     * Run the command
     *
     * @param environment info about variable. In fact, it is used only in assignment
     * @param stdin InputStream
     * @return ExecutionResult: (finishFlag, inputStream). Put to inputStream result of execute
     * @throws IOException throws when file can't be open.
     */
    ExecutionResult execute(Environment environment, InputStream stdin) throws IOException;
}
