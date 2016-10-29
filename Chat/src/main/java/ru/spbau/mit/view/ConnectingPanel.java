package ru.spbau.mit.view;

import ru.spbau.mit.control.SwingGUIManager;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ConnectingPanel extends JPanel {
    public ConnectingPanel(SwingGUIManager mng) {
        JButton button = new JButton(":(");
        ActionListener actionListener = evt -> mng.abortConnection();

        button.addActionListener(actionListener);

        add(button);
    }
}
