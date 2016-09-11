import java.io.*;
import java.util.Scanner;

public class WcCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        ExecutionResult executionResult = new ExecutionResult();

        int cntLine = 0, cntWord = 0, cntBytes = 0;

        if (args.size() > 0) {
            for (String fileName : args) {
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
