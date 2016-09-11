import java.io.InputStream;

public class PipeCommand extends Statement {
    Statement left;
    Statement right;

    public PipeCommand(Statement left, Statement right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        ExecutionResult executionResult = left.execute(environment, stdin);
        if (executionResult.isFinishFlag()) {
            return executionResult;
        }
        return right.execute(environment, executionResult.getStdout());
    }
}
