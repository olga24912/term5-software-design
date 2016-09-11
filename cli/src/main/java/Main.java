import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Lexer lexer = new Lexer();
        Environment environment = new Environment();
        Parser parser = new Parser();
        boolean finishFlag = false;
        while(!finishFlag) {
            try {
                String currentLine = in.nextLine();
                ArrayList<Token> tokens;
                tokens = lexer.parseString(currentLine);
                String newLine = environment.substituteVariable(tokens);
                tokens = lexer.parseString(newLine);
                CommandLine cl = parser.buildAST(tokens);
                ExecutionResult executionResult = cl.execute(environment, System.in);

                if (executionResult.getStdout() != null) {
                    byte[] buffer = new byte[1024];
                    int readBytes;
                    try {
                        while ((readBytes = executionResult.getStdout().read(buffer)) > 0) {
                            System.out.write(buffer, 0, readBytes);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                finishFlag = executionResult.isFinishFlag();
            } catch (ParsingException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
