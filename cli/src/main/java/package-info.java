/**
 * This project contain next classes:
 *  Main:
 *      methods: main() - contain Environment, Lexer, Parse
 *                        read string,
 *                        split to Token use Lexer,
 *                        use Environment for substitute variable,
 *                        use Lexer second time,
 *                        use Parser for create AST and execute result.
 *
 * Lexer:
 *      methods: parseString - take string and return ArrayList<Token> split string to tokens
 *
 * Parser:
 *      methods:  buildAST - take ArrayList<Token> and return CommandLine(AST of command)
 *
 * Environment:
 *      fields: map - from variable name to value
 *      methods: substituteVariable - take ArrayList<Tokens> and string from this tokens, where var change to value
 *               set/getVariable
 *
 * Token:
 *     fields: textValue and type(textValue for easy construct from tokens to String, type is enum TokenType)
 *
 *
 *hierarchy of classes AST:
 *
 * CommandLine:
 *      -Assignment
 *      -Statement:
 *          -PipeCommand
 *          -Command:
 *              -CatCommand
 *              -EchoCommand
 *              -ExitCommand
 *              -PwdCommand
 *              -UnknownCommand
 *              -WcCommand
 *
 *      methods: execute() - take Environment and inputStream
 *                           return ExecutionResult (result in inputStream and FinishFlag(become true in exit command))
 *
 * Utils: class with help function
 *
 **/