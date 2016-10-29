package ru.spbau.mit.view;

import org.apache.log4j.Logger;
import ru.spbau.mit.control.SwingGUIManager;
import ru.spbau.mit.network.Client;
import ru.spbau.mit.network.ConnectionCreator;
import ru.spbau.mit.network.Server;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginPanel extends JPanel  {
    private static final Logger LOG = Logger.getLogger(LoginPanel.class);

    private JComboBox<String> comboBox;
    private JTextField textFieldHost;
    private JTextField textFieldPort;
    private JTextField textFieldName;

    private SwingGUIManager mng;

    public LoginPanel(SwingGUIManager mng) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.mng = mng;

        JButton buttonOK = createButton();
        comboBox = createComboBox();
        JLabel labelHost = new JLabel("host:");
        JLabel labelPort = new JLabel("port:");
        JLabel labelName = new JLabel("name:");
        textFieldHost = new JTextField();
        textFieldPort = new JTextField();
        textFieldName = new JTextField();

        add(comboBox);
        add(labelName);
        add(textFieldName);
        add(labelHost);
        add(textFieldHost);
        add(labelPort);
        add(textFieldPort);
        add(buttonOK);
    }

    private JComboBox<String> createComboBox() {
        String[] options = {"Client", "Server"};

        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setEditable(false);

        ActionListener actionListener = evt -> {
            if (comboBox.getSelectedIndex() == 0) {
                textFieldHost.setEditable(true);
            } else {
                textFieldHost.setEditable(false);
            }
        };

        comboBox.addActionListener(actionListener);

        return comboBox;
    }

    private JButton createButton() {
        JButton buttonOK;
        buttonOK = new JButton("OK");
        buttonOK.setVerticalTextPosition(AbstractButton.CENTER);
        buttonOK.setHorizontalTextPosition(AbstractButton.LEADING);
        buttonOK.setActionCommand("OK");

        ActionListener actionListener = evt -> {
            ConnectionCreator creator;
            if (comboBox.getSelectedIndex() == 0) {
                creator = new Client(textFieldName.getText(),
                        textFieldHost.getText(), Integer.valueOf(textFieldPort.getText()));
            } else {
                try {
                    creator = new Server(textFieldName.getText(),
                                Integer.valueOf(textFieldPort.getText()));
                } catch (IOException e) {
                    LOG.warn(e.getMessage());
                    return;
                }

            }

            mng.startConnecting(creator);
        };

        buttonOK.addActionListener(actionListener);
        return buttonOK;
    }

}
