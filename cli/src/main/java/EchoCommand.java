import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class EchoCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        ExecutionResult result = new ExecutionResult();
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (String arg : args) {
            if (!first) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(arg);
            first = false;
        }

        stringBuilder.append('\n');

        result.setExitCode(0);
        result.setFinishFlag(false);
        result.setStdout(new ByteArrayInputStream(stringBuilder.toString().getBytes()));
        return result;
    }
}
