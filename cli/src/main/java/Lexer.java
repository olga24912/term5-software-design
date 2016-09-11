import java.util.ArrayList;

public class Lexer {
    private String string;
    private ArrayList<Token> tokens;

    public ArrayList<Token> parseString(String currentLine) {
        string = currentLine + ' ';
        tokens = new ArrayList<>();
        parseStringFirstText(0);
        if (tokens.get(tokens.size() - 1).getType() == TokenType.TokenSpace) {
            tokens.remove(tokens.size() - 1);
        }
        return tokens;
    }

    private int parseStringFirstText(int leftPosition) {
        int rightPosition = readWord(leftPosition);

        if (rightPosition != leftPosition) {
            if (string.charAt(rightPosition) != '=') {
                tokens.add(new Token(string.substring(leftPosition, rightPosition), TokenType.TokenCommand));
            } else {
                tokens.add(new Token(string.substring(leftPosition, rightPosition), TokenType.TokenVariableName));
            }
        }

        switch (string.charAt(rightPosition)) {
            case ' ':
                tokens.add(new Token(" ", TokenType.TokenSpace));
                return parseStringNotFirstText(rightPosition + 1);
            case '=':
                tokens.add(new Token("=", TokenType.TokenAssign));
                return parseStringAfterAssign(rightPosition + 1);
            case '|':
                tokens.add(new Token("|", TokenType.TokenPipe));
                return parseStringFirstText(rightPosition + 1);
        }
        return rightPosition;
    }

    private int parseStringNotFirstText(int leftPosition) {
        int rightPosition;
        if (leftPosition == string.length()) {
            return leftPosition;
        }
        if (string.charAt(leftPosition) == '$') {
            rightPosition = parseStringVariable(leftPosition);
        } else if (string.charAt(leftPosition) == '"') {
            tokens.add(new Token("\"", TokenType.TokenDoubleQuote));
            rightPosition = parseStringInDoubleQuote(leftPosition + 1);
        } else if (string.charAt(leftPosition) == '\'') {
            tokens.add(new Token("\'", TokenType.TokenQuote));
            rightPosition = parseStringInQuote(leftPosition + 1);
        } else {
            rightPosition = readWord(leftPosition);
            if (leftPosition != rightPosition) {
                tokens.add(new Token(string.substring(leftPosition, rightPosition), TokenType.TokenText));

            }
        }
        switch (string.charAt(rightPosition)) {
            case ' ':
                tokens.add(new Token(" ", TokenType.TokenSpace));
                return parseStringNotFirstText(rightPosition + 1);
            case '|':
                tokens.add(new Token("|", TokenType.TokenPipe));
                tokens.add(new Token(" ", TokenType.TokenSpace));
                return parseStringFirstText(rightPosition + 2);
        }
        return rightPosition;
    }

    private int parseStringAfterAssign(int leftPosition) {
        switch (string.charAt(leftPosition)) {
            case '$':
                return parseStringVariable(leftPosition);
            case '"':
                tokens.add(new Token("\"", TokenType.TokenDoubleQuote));
                return parseStringInDoubleQuote(leftPosition + 1);
            case '\'':
                tokens.add(new Token("'", TokenType.TokenQuote));
                return parseStringInQuote(leftPosition + 1);
            default:
                int rightPosition = readWord(leftPosition);
                tokens.add(new Token(string.substring(leftPosition, rightPosition), TokenType.TokenText));
                return rightPosition;
        }
    }

    private int parseStringInQuote(int leftPosition) {
        int rightPosition = leftPosition;
        while (string.charAt(rightPosition) != '\'') {
            ++rightPosition;
        }
        tokens.add(new Token(string.substring(leftPosition, rightPosition), TokenType.TokenText));
        tokens.add(new Token("'", TokenType.TokenQuote));
        return rightPosition + 1;
    }

    private int parseStringInDoubleQuote(int leftPosition) {
        int rightPosition= leftPosition;
        while (string.charAt(rightPosition) != '"') {
            if (string.charAt(rightPosition) == '$') {
                rightPosition = parseStringVariable(rightPosition);
                leftPosition = rightPosition;
            } else {
                while (string.charAt(rightPosition) != '$' && string.charAt(rightPosition) != '"') {
                    ++rightPosition;
                }
                tokens.add(new Token(string.substring(leftPosition, rightPosition), TokenType.TokenText));
                leftPosition = rightPosition;
            }
        }
        tokens.add(new Token("\"", TokenType.TokenDoubleQuote));
        return rightPosition + 1;
    }

    private int parseStringVariable(int leftPosition) {
        int rightPosition = readWord(leftPosition + 1);
        tokens.add(new Token(string.substring(leftPosition, rightPosition), TokenType.TokenVariable));
        return rightPosition;
    }

    private int readWord(int leftPosition) {
        int rightPosition = leftPosition;
        while(!isSpecial(string.charAt(rightPosition))) {
            ++rightPosition;
        }
        return rightPosition;
    }

    private Boolean isSpecial(char c) {
        return c == '$' || c == '|' || c == '=' || c == '\'' || c == '"' || c == ' ';
    }

}
