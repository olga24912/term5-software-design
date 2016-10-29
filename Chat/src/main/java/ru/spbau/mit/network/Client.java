package ru.spbau.mit.network;

import ru.spbau.mit.control.GUIManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * create connection from client part
 */
public class Client implements ConnectionCreator {
    private static final Logger LOG = Logger.getLogger(Client.class);

    private String clientHost;
    private String clientName;
    private int clientPort;

    private Thread threadConnect;

    public Client(String name, String host, Integer port) {
        clientName = name;
        clientHost = host;
        clientPort = port;
    }

    @Override
    public void connect(GUIManager mng) {
        threadConnect = new Thread(() -> {
            Socket socket = null;
            while (socket == null) {
                if (Thread.interrupted()) return;
                try {
                    socket = new Socket(clientHost, clientPort);
                } catch (IOException e) {
                    LOG.trace(e.getMessage());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        LOG.trace(e.getMessage());
                        return;
                    }
                }
            }

            try {
                Connection connect = new Connection(socket, clientName, mng);
                mng.processConnection(connect);
            } catch (IOException e) {
                mng.processDisconnected();
            }
        });

        threadConnect.start();
    }

    @Override
    public void cancel() {
        threadConnect.interrupt();
    }
}