package ru.spbau.mit.Command;

import ru.spbau.mit.Environment;
import ru.spbau.mit.ExecutionResult;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Class for 'echo' command
 *
 * ignore inputStream,
 * write to inputStream in ExecutionResult concatenation of args with space between them.
 */
public class EchoCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        ExecutionResult result = new ExecutionResult();
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (String arg : getArgs()) {
            if (!first) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(arg);
            first = false;
        }

        stringBuilder.append('\n');

        result.setStdout(new ByteArrayInputStream(stringBuilder.toString().getBytes()));
        return result;
    }
}
