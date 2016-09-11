import java.io.InputStream;

public class ExitCommand extends Command {
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        ExecutionResult executionResult = new ExecutionResult();
        executionResult.setFinishFlag(true);
        return executionResult;
    }
}
