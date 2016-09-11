import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for store values of variables
 */

public class Environment {
    private Map<String, String> variable = new HashMap<>();

    /**
     * Substitute Variable.
     *
     * If variable doesn't exists put "".
     *
     * @param tokens that string split on.
     * @return change variable token to value and merge tokens to string
     */
    public String substituteVariable(ArrayList<Token> tokens) {
        StringBuilder  result = new StringBuilder();
        for (Token token : tokens) {
            result.append(token.substitute(this));
        }
        return result.toString();
    }

    // get variable value
    public String getVariable(String name) {
        if (variable.containsKey(name)) {
            return variable.get(name);
        }
        return "";
    }

    // change variable value
    public void setVariable(String name, String value) {
        variable.put(name, value);
    }
}
