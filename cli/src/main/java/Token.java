public class Token {
    private String textValue;
    private TokenType type;

    public Token(String textValue, TokenType type) {
        this.textValue = textValue;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        return textValue != null ? textValue.equals(token.textValue) : token.textValue == null && type == token.type;
    }

    @Override
    public int hashCode() {
        int result = textValue != null ? textValue.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public TokenType getType() {

        return type;
    }

    public String getTextValue() {

        return textValue;
    }

    @Override
    public String toString() {
        return "Token{" +
                "textValue='" + textValue + '\'' +
                ", type=" + type +
                '}';
    }


    public String substitute(Environment environment) {
        if (type != TokenType.TokenVariable) {
            return textValue;
        } else {
            return environment.getVariable(textValue.substring(1));
        }
    }
}
