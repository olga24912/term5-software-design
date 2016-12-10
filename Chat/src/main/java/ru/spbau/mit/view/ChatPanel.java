package ru.spbau.mit.view;

import org.apache.log4j.Logger;
import ru.spbau.mit.Message;
import ru.spbau.mit.control.SwingGUIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Main panel for chat
 */
public class ChatPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(ChatPanel.class);

    private static final long deltaTime = 500;

    private static JLabel titleText = null;
    private static JTextArea chatText = null;
    private static JTextField chatLine = null;

    private long lastTypingTime = 0;

    public ChatPanel(SwingGUIManager mng) throws IOException {
        super(new BorderLayout());

        titleText = new JLabel();

        chatText = new JTextArea(10, 20);
        chatText.setLineWrap(true);
        chatText.setEditable(false);
        chatText.setForeground(Color.blue);

        JScrollPane chatTextPane = new JScrollPane(chatText,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        chatLine = new JTextField();
        chatLine.setEnabled(true);

        ActionListener actionListener = evt -> {
            String text = chatLine.getText();
            chatLine.setText("");
            mng.sendMsg(text);
        };

        chatLine.addActionListener(actionListener);
        chatLine.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                mng.sendTypingNotification();
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        add(titleText, BorderLayout.NORTH);
        add(chatLine, BorderLayout.SOUTH);
        add(chatTextPane, BorderLayout.CENTER);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (lastTypingTime + deltaTime > System.currentTimeMillis()) {
                        titleText.setText("typing...");
                    } else {
                        titleText.setText("");
                    }
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        LOG.trace(e.getMessage());
                    }
                }
            }
        }).start();

        setPreferredSize(new Dimension(200, 200));
    }

    public void newMsg(Message msg) {
        LOG.debug("new msg");
        chatText.append(msg.getName() + ":    " + msg.getMsg() + "\n");
        chatText.setCaretPosition(chatText.getDocument().getLength());
    }

    public void typing() {
        lastTypingTime = System.currentTimeMillis();
    }
}
