package ru.spbau.mit;

import java.io.InputStream;

public class ExecutionResult {
    private boolean finishFlag;
    private InputStream stdout;

    public boolean isFinishFlag() {
        return finishFlag;
    }

    public InputStream getStdout() {
        return stdout;
    }

    public void setFinishFlag(boolean finishFlag) {
        this.finishFlag = finishFlag;
    }

    public void setStdout(InputStream stdout) {
        this.stdout = stdout;
    }
}
