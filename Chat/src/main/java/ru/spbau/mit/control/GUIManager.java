package ru.spbau.mit.control;

import ru.spbau.mit.Message;
import ru.spbau.mit.network.Connection;

/**
 * interface for interaction between view and network parts
 */
public interface GUIManager {
    void processNewMessage(Message msg); //handling getting new message.
    void processTyping(); //handling notification about companion typing.
    void processConnection(Connection connection); //Call when new connection was set.
    void processDisconnected(); //handling problems with network
}
