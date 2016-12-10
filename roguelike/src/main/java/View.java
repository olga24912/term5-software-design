import model.Character;
import model.GameObject;
import model.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by olga on 10.12.16.
 */
public class View {
    private JTextArea output;
    private Controller controller;

    public View(Controller controller) {
        output = new JTextArea(20, 50);
        output.setFont(new Font("monospaced", Font.PLAIN, 12));

        JFrame console = new JFrame("roguelike");

        console.add(output);
        console.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        console.pack();
        console.setVisible(true);

        this.controller = controller;

        output.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                controller.makeMove(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                output.setText(output.getText().substring(0, output.getText().length() - 1));
                output.append("  ");
            }
        });
    }

    public void drawMap(GameObject[][] map) {
        StringBuilder bldr = new StringBuilder();

        for (GameObject[] aMap : map) {
            for (GameObject anAMap : aMap) {
                bldr.append(anAMap.getSymbol());
            }
            bldr.append('\n');
        }

        bldr.append("\n\n");
        output.setText(bldr.toString());
    }

    public void drawGameOver() {
        output.setText("GAME OVER ");
    }

    public void drawHelp() {
        output.setText("asdw - character handling\n jkli - shoot monster or take tool\n" +
                "your turn first\n" +
                "h - help\n" +
                "t - show your tools\n" +
                "c - continue play  ");
    }

    public void drawTools(Character character) {
        ArrayList<Tool> tools = character.getTools();
        output.setText("Tools count:  " + tools.size());
    }

    public void drawWin() {
        output.setText("YOU WIN!  ");
    }
}
