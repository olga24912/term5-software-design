package ru.spbau.mit.view;

import ru.spbau.mit.control.Controller;
import ru.spbau.mit.model.game_objects.Character;
import ru.spbau.mit.model.game_objects.GameObject;
import ru.spbau.mit.model.game_objects.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

//Class for displaying game.
public class View {
    private JTextArea output;

    public View(final Controller controller) {
        output = new JTextArea(20, 50);
        output.setFont(new Font("monospaced", Font.PLAIN, 12));

        JFrame console = new JFrame("roguelike");

        console.add(output);
        console.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        console.pack();
        console.setVisible(true);

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

    //display map
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

    //display the end of game
    public void drawGameOver() {
        output.setText("GAME OVER ");
    }

    //display help
    public void drawHelp() {
        output.setText("asdw - character handling\n jkli - shoot monster or take tool\n" +
                "your turn first\n" +
                "h - help\n" +
                "t - show your tools\n" +
                "c - continue play  ");
    }

    //display information about tools
    public void drawTools(Character character) {
        ArrayList<Tool> tools = character.getTools();
        output.setText("Tools count:  " + tools.size());
    }

    //display the end of game when character win.
    public void drawWin() {
        output.setText("YOU WIN!  ");
    }
}
