package ru.spbau.mit.control;

import org.apache.log4j.Logger;
import ru.spbau.mit.Message;
import ru.spbau.mit.network.Connection;
import ru.spbau.mit.network.ConnectionCreator;
import ru.spbau.mit.view.ChatPanel;
import ru.spbau.mit.view.ConnectingPanel;
import ru.spbau.mit.view.LoginPanel;

import javax.swing.*;
import java.io.IOException;

/**
 * Control for swing GUI.
 */
public class SwingGUIManager implements GUIManager {
    private static final Logger LOG = Logger.getLogger(SwingGUIManager.class);

    private JFrame loginFrame;
    private JFrame connectionFrame;
    private JFrame chatFrame;

    private ChatPanel chatPanel;

    private ConnectionCreator creator;
    private Connection connection;

    public SwingGUIManager() {
        LoginPanel loginPanel = new LoginPanel(this);
        loginFrame = new JFrame("Login");

        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loginFrame.add(loginPanel);

        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    @Override
    public void processConnection(Connection connection) {
        connectionFrame.setVisible(false);
        this.connection = connection;
        chatFrame = new JFrame("Chat");
        try {
            chatPanel = new ChatPanel(this);
            chatFrame.add(chatPanel);
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }

        chatFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chatFrame.pack();
        chatFrame.setVisible(true);
    }

    @Override
    public void processDisconnected() {
        chatFrame.setVisible(false);
        chatFrame.dispose();

        loginFrame.setVisible(true);
    }

    //Send message
    public void sendMsg(String text) {
        connection.sendMsg(text);
    }

    @Override
    public void processNewMessage(Message msg) {
        LOG.info(msg.getName() + " " + msg.getMsg());
        chatPanel.newMsg(msg);
    }

    //Start interaction with network
    public void startConnecting(ConnectionCreator creator) {
        this.creator = creator;
        loginFrame.setVisible(false);

        connectionFrame = new JFrame("Connection");
        connectionFrame.add(new ConnectingPanel(this));

        connectionFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        connectionFrame.pack();
        connectionFrame.setVisible(true);

        try {
            creator.connect(this);
        } catch (IOException e) {
            connectionFrame.dispose();
            connectionFrame = null;
            loginFrame.setVisible(true);
            LOG.warn(e.getMessage());
        }
    }

    //Stop connection
    public void abortConnection() {
        creator.cancel();
        loginFrame.setVisible(true);
        connectionFrame.setVisible(false);
        connectionFrame.dispose();
        connectionFrame = null;
    }
}
