import java.io.InputStream;

public interface CommandLine {
    ExecutionResult execute(Environment environment,InputStream stdin);
}
