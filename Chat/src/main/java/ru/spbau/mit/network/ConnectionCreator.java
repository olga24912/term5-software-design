package ru.spbau.mit.network;

import ru.spbau.mit.control.GUIManager;

import java.io.IOException;

/**
 * interface for create connection
 */
public interface ConnectionCreator {
     //Start connection
     void connect(GUIManager mng) throws IOException;
     //Stop connection
     void cancel();
}
