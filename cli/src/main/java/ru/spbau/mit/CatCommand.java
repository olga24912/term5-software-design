package ru.spbau.mit;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Class for 'cat' command
 *
 * args - it is names of files
 * write concatenation content of files,
 * if count of args = 0, write from inputStream
 * if count of args > 0, ignore inputStream
 */
public class CatCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        ExecutionResult executionResult = new ExecutionResult();
        ArrayList<Byte> bytesFromFiles = new ArrayList<>();

        if (argSize() > 0) {
            for (String fileName : getArgs()) {
                FileInputStream fileInputStream = new FileInputStream(fileName);
                Utils.fromInputStreamToBytes(fileInputStream, bytesFromFiles);
            }
            byte[] bytes = new byte[bytesFromFiles.size()];
            for (int i = 0; i < bytesFromFiles.size(); ++i) {
                bytes[i] = bytesFromFiles.get(i);
            }
            executionResult.setStdout(new ByteArrayInputStream(bytes));
            return executionResult;
        } else {
            executionResult.setStdout(stdin);
            return executionResult;
        }
    }
}
