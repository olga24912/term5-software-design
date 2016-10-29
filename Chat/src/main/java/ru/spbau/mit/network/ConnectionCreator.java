package ru.spbau.mit.network;

import ru.spbau.mit.control.GUIManager;

import java.io.IOException;

/**
 * interface for create connection
 */
public interface ConnectionCreator {
     // Call for starting connection
     void connect(GUIManager mng) throws IOException;
     void cancel();
}
