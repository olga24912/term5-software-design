import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, String> variable = new HashMap<>();

    public String substituteVariable(ArrayList<Token> tokens) {
        StringBuilder  result = new StringBuilder();
        for (Token token : tokens) {
            result.append(token.substitute(this));
        }
        return result.toString();
    }

    public String getVariable(String name) {
        if (variable.containsKey(name)) {
            return variable.get(name);
        }
        return "";
    }

    public void setVariable(String name, String value) {
        variable.put(name, value);
    }
}
