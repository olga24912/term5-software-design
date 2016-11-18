import java.io.IOException;
import java.util.Scanner;

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
        }
    }

    private static void mapCondition() throws IOException {
        Scanner s = new Scanner(System.in);
        String str = s.nextLine();

        state.setMove(str.charAt(0));
        state.doTerm();
        mapCondition();
    }

    private static void helpCondition() {

    }

    private static void toolsCondition() {

    }
}
