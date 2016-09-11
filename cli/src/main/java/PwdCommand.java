import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PwdCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        ExecutionResult executionResult = new ExecutionResult();
        String current = "";
        try {
            current = new java.io.File( "." ).getCanonicalPath() + '\n';
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream outputStream = new ByteArrayInputStream(current.getBytes(StandardCharsets.UTF_8));
        executionResult.setStdout(outputStream);
        return executionResult;
    }
}
