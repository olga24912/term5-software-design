package ru.spbau.mit.view;

import org.apache.log4j.Logger;
import ru.spbau.mit.Message;
import ru.spbau.mit.control.SwingGUIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(ChatPanel.class);

    public static JTextArea chatText = null;
    public static JTextField chatLine = null;

    public ChatPanel(SwingGUIManager mng) throws IOException {
        super(new BorderLayout());

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

        add(chatLine, BorderLayout.SOUTH);
        add(chatTextPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(200, 200));
    }

    public void newMsg(Message msg) {
        LOG.debug("new msg");
        chatText.append(msg.getName() + ":    " + msg.getMsg() + "\n");
        chatText.setCaretPosition(chatText.getDocument().getLength());
    }
}
