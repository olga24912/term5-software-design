import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static State state;

    public static void main(String[] args) {
        StateBuilder stateBuilder = new StateBuilder();
        state = stateBuilder.build();

        try {
            state.draw();
            mapCondition();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void mapCondition() throws IOException, InterruptedException {
//        Scanner s = new Scanner(System.in);
//        String str = s.nextLine();

        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();

        //Console console = System.console();
        //Reader reader = console.reader();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char s = (char)br.read();
        state.setMove(s);
        state.doTerm();

        mapCondition();
    }

    private static void helpCondition() {

    }

    private static void toolsCondition() {

    }
}
