import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PwdCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) throws IOException {
        ExecutionResult executionResult = new ExecutionResult();
        String current;
        current = new java.io.File( "." ).getCanonicalPath() + '\n';
        InputStream outputStream = new ByteArrayInputStream(current.getBytes(StandardCharsets.UTF_8));
        executionResult.setStdout(outputStream);
        return executionResult;
    }
}
