import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CatCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        ExecutionResult executionResult = new ExecutionResult();
        ArrayList<Byte> bytesFromFiles = new ArrayList<>();

        if (args.size() > 0) {
            for (String fileName : args) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(fileName);
                    Utils.fromInputStreamToBytes(fileInputStream, bytesFromFiles);
                } catch (IOException e) {
                }
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