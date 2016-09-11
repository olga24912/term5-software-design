import java.util.ArrayList;

/**
 * abstract class for all commands (like echo, pwd, wc, ..., NOT for: =, |)
 */

public abstract class Command extends Statement {
    protected ArrayList<String> args;

    public Command() {
        args = new ArrayList<>();
    }

    //add argument for command
    public void addArg(String val) {
        args.add(val);
    }
}
