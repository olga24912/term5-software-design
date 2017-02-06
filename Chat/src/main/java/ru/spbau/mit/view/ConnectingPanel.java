package ru.spbau.mit.view;

import ru.spbau.mit.control.SwingGUIManager;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Waiting connection panel with one button - return to login panel
 */
public class ConnectingPanel extends JPanel {
    public ConnectingPanel(SwingGUIManager mng) {
        JButton button = new JButton(":(");
        ActionListener actionListener = evt -> mng.abortConnection();

        button.addActionListener(actionListener);

        add(button);
    }
}
