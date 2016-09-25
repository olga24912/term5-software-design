package ru.spbau.mit;

import java.io.InputStream;
/**
 * Command for a=x
 * Put for variable new value
 */
public class Assignment implements CommandLine {
    private String var;
    private String data;

    /**
     * var=data
     * @param var - name of variable
     * @param data - new value
     */
    public Assignment(String var, String data) {
        this.var = var;
        this.data = data;
    }

    /**
     * put new value into environment
     * @param environment info about variable in fact use only in assignment
     * @param stdin InputStream
     * @return empty ExectionResult
     */
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        environment.setVariable(var, data);
        return new ExecutionResult();
    }
}
