package ru.spbau.mit.network;

import ru.spbau.mit.control.GUIManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * create connection from server part
 */
public class Server implements ConnectionCreator {
    private static final Logger LOG = Logger.getLogger(Server.class);

    private ServerSocket serverSocket;
    private String name;

    public Server(String name, Integer port) throws IOException {
        this.name = name;
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void connect(GUIManager mng) throws IOException {
        Thread connectThread = new Thread(() -> {
            Socket socket = accept();
            if (socket != null) {
                LOG.info("catch socket");
                try {
                    Connection connect = new Connection(socket, name, mng);
                    mng.processConnection(connect);
                } catch (IOException e) {
                    mng.processDisconnected();
                }
            }
        });

        connectThread.start();
    }

    private Socket accept() {
        try {
            return this.serverSocket.accept();
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }
    }
}
