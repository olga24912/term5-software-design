import java.util.ArrayList;

public abstract class Command extends Statement {
    protected ArrayList<String> args;

    public Command() {
        args = new ArrayList<>();
    }

    public void addArg(String val) {
        args.add(val);
    }
}
