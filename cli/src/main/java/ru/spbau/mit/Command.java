package ru.spbau.mit;

import java.util.ArrayList;

/**
 * abstract class for all commands (like echo, pwd, wc, ..., NOT for: =, |)
 */
public abstract class Command extends Statement {
    private ArrayList<String> args;

    public Command() {
        args = new ArrayList<>();
    }

    public String getArg(int i) {
        return args.get(i);
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public int argSize() {
        return args.size();
    }

    /**
     * add argument for command
     * @param val - value of new arg
     */
    public void addArg(String val) {
        args.add(val);
    }
}
