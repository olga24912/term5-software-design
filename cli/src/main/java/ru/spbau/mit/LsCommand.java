package ru.spbau.mit;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by the7winds on 24.09.16.
 */

/**
 * class for "ls" command: displays all thing inside input directory
 */

public class LsCommand extends Command {

    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        File[] files = new File[0];

        if (argSize() > 0) {
            File directory = new File(getArg(0));
            if (directory.exists()) {
                if (directory.isDirectory()) {
                    files = new File(getArg(0)).listFiles();
                } else {
                    files = new File[]{directory};
                }
            }
        } else {
            files = new File(System.getProperty("user.dir")).listFiles();
        }

        StringBuilder output = new StringBuilder();

        for (File file : files) {
            output.append(file.getName());
            output.append(" ");
        }

        ExecutionResult executionResult = new ExecutionResult();
        executionResult.setStdout(new ByteArrayInputStream(output.toString().getBytes()));

        return executionResult;
    }
}
