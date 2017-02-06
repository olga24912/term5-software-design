package ru.spbau.mit;

import java.io.InputStream;
/**
 * Command for a=x
 * Put for variable new value
 */
public class Assignment implements CommandLine {
    private String var;
    private String data;

    // var - name of variable, data - new value (var=data)
    public Assignment(String var, String data) {
        this.var = var;
        this.data = data;
    }

    // put in environment new value
    @Override
    public ExecutionResult execute(Environment environment, InputStream stdin) {
        environment.setVariable(var, data);
        return new ExecutionResult();
    }
}
