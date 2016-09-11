import java.io.InputStream;

public class Assignment implements CommandLine {
    String var, data;
    public Assignment(String var, String data) {
        this.var = var;
        this.data = data;
    }

    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        environment.setVariable(var, data);
        return new ExecutionResult();
    }
}
