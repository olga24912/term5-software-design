package ru.spbau.mit;

import java.io.*;
import java.util.Scanner;

/**
 * Class for 'wc'command
 *
 * args - names of files;
 *
 * ignore inputStream if args > 0,
 * if args = 0 write count of lines, words and bytes of input to inputStream in ExecutionResult
 * if args > 0 write summery count of lines, words and bytes in all files to inputStream in ExecutionResult
 */
public class WcCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        ExecutionResult executionResult = new ExecutionResult();

        int cntLine = 0, cntWord = 0, cntBytes = 0;

        if (argSize() > 0) {
            for (String fileName : getArgs()) {
                FileInputStream fileInputStream = new FileInputStream(fileName);
                File file = new File(fileName);
                cntBytes += file.length();
                try (Scanner sc = new Scanner(fileInputStream)) {
                    while (sc.hasNext()) {
                        sc.next();
                        ++cntWord;
                    }
                }
                LineNumberReader lnr = new LineNumberReader(new FileReader(file));
                lnr.skip(Long.MAX_VALUE);
                cntLine += lnr.getLineNumber() + 1;
                lnr.close();
            }
        } else {
            byte[] buffer = new byte[1024];
            int readBytes;
            byte prev = 'a';
            while ((readBytes = stdin.read(buffer)) > 0) {
                for (int i = 0; i < readBytes; ++i) {
                    if (buffer[i] == '\n') {
                        ++cntLine;
                    }
                    if ((buffer[i] == '\n' || buffer[i] == ' ') && prev != '\n' && prev != ' ') {
                        ++cntWord;
                    }
                    prev = buffer[i];
                }
                cntBytes += readBytes;
            }

            if (prev != '\n' && prev != ' ') {
                ++cntWord;
            }
        }

        String result = String.valueOf(cntLine) + " " + String.valueOf(cntWord) +
                " " + String.valueOf(cntBytes) + "\n";

        executionResult.setStdout(new ByteArrayInputStream(result.getBytes()));
        return executionResult;
    }
}
