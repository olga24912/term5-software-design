import java.io.InputStream;

public class ExecutionResult {
    private int exitCode;
    private boolean finishFlag;
    private InputStream stdout;

    public int getExitCode() {
        return exitCode;
    }

    public boolean isFinishFlag() {
        return finishFlag;
    }

    public InputStream getStdout() {
        return stdout;
    }

    public void setExitCode(int exitCode) {

        this.exitCode = exitCode;
    }

    public void setFinishFlag(boolean finishFlag) {
        this.finishFlag = finishFlag;
    }

    public void setStdout(InputStream stdout) {
        this.stdout = stdout;
    }
}
