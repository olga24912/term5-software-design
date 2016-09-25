package ru.spbau.mit.Command;

import ru.spbau.mit.Environment;
import ru.spbau.mit.ExecutionResult;
import ru.spbau.mit.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class for command that we have not realisation for.
 *
 * Try to call command use process for that
 * ignore inputStream,.
 */
public class UnknownCommand extends Command {
    private String name;

    public UnknownCommand(String name) {
        this.name = name;
    }

    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        ExecutionResult executionResult = new ExecutionResult();

        String[] command = new String[argSize() + 1];
        command[0] = name;
        int i = 1;
        for (String arg : getArgs()) {
            command[i] = arg;
            ++i;
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        if (stdin == System.in) {
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        } else {
            processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
        }
        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);

        Process process = processBuilder.start();

        if (stdin != System.in) {
            OutputStream processStdin = process.getOutputStream();
            Utils.fromInputStreamToOutputStream(stdin, processStdin);
            processStdin.close();
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executionResult.setStdout(process.getInputStream());
        return executionResult;
    }
}
