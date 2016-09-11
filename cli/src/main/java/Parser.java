import java.util.ArrayList;

public class Parser {
    ArrayList<Token> tokens;
    int position;

    public CommandLine buildAST(ArrayList<Token> tokens) {
        this.tokens = tokens;
        position = 0;
        if (tokens.get(0).getType() == TokenType.TokenVariableName) {
            if (tokens.get(1).getType() != TokenType.TokenAssign) {
                throw  new AssertionError();
            }
            return new Assignment(tokens.get(0).getTextValue(), tokens.get(2).getTextValue());
        } else {
            return parseStatement();
        }
    }

    private Statement parseStatement() {
        skipBlank();
        if (currentToken().getType() != TokenType.TokenCommand) {
            throw new AssertionError();
        }

        Command currentCommand;
        switch (currentToken().getTextValue()) {
            case "echo":
                currentCommand = new EchoCommand();
                break;
            case "exit":
                currentCommand = new ExitCommand();
                break;
            case "pwd":
                currentCommand = new PwdCommand();
                break;
            case "cat":
                currentCommand = new CatCommand();
                break;
            case "wc":
                currentCommand = new WcCommand();
                break;
            default:
                currentCommand = new UnknownCommand(currentToken().getTextValue());
                break;
        }

        ++position;
        skipBlank();
        while(position < tokens.size() && currentToken().getType() != TokenType.TokenPipe) {
            currentCommand.addArg(parseArg());
            skipBlank();
        }

        if (position == tokens.size()) {
            return currentCommand;
        } else {
            ++position;
            return new PipeCommand(currentCommand, parseStatement());
        }
    }

    private String parseArg() {
        skipBlank();
        Token token = currentToken();
        if (token.getType() == TokenType.TokenText) {
            ++position;
            return token.getTextValue();
        } else if (token.getType() == TokenType.TokenQuote || token.getType() == TokenType.TokenDoubleQuote) {
            ++position;
            if (currentToken().getType() != TokenType.TokenText) {
                throw new AssertionError();
            }
            Token retToken = currentToken();
            ++position;
            if (currentToken().getType() != token.getType()) {
                throw new AssertionError();
            }
            ++position;
            return retToken.getTextValue();
        }
        throw new AssertionError();
    }

    private void skipBlank() {
        while(position < tokens.size() && currentToken().getType() == TokenType.TokenSpace) {
            ++position;
        }
    }

    private Token currentToken() {
        return tokens.get(position);
    }

}