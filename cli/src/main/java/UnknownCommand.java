import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UnknownCommand extends Command {
    String name;

    public UnknownCommand(String name) {
        this.name = name;
    }

    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        ExecutionResult executionResult = new ExecutionResult();

        String[] command = new String[args.size() + 1];
        command[0] = name;
        int i = 1;
        for (String arg : args) {
            command[i] = arg;
            ++i;
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        if (stdin == System.in) {
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
        } else {
            processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
        }
        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);

        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (stdin != System.in) {
            OutputStream processStdin = process.getOutputStream();
            Utils.fromInputStreamToOutputStream(stdin, processStdin);
            try {
                processStdin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executionResult.setStdout(process.getInputStream());
        executionResult.setExitCode(process.exitValue());
        return executionResult;
    }
}
