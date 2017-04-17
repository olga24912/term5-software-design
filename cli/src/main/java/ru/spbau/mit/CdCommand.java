package ru.spbau.mit;

import java.io.*;

/**
 * Created by the7winds on 24.09.16.
 */

/**
 * class for "cd" command: first arg is new directory
 */

public class CdCommand extends Command {

    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        if (argSize() > 0) {
            String directoryName = getArg(0);
            File newDirectory = new File(directoryName);

            if (newDirectory.isDirectory()) {
                System.setProperty("user.dir", newDirectory.getCanonicalPath());
            }
        }

        return new ExecutionResult();
    }
}
