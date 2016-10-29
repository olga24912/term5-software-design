package ru.spbau.mit.network;

import org.apache.log4j.Logger;
import ru.spbau.mit.Message;
import ru.spbau.mit.control.GUIManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private static final Byte MSG_QUERY = 0;
    private static final Byte CONNECT_QUERY = 1;

    private static final Logger LOG = Logger.getLogger(Connection.class);

    private Socket socket;
    private String myName;

    private String hisName;

    private GUIManager mng;

    public Connection(Socket socket, String myName, GUIManager mng) throws IOException {
        this.socket = socket;
        this.myName = myName;
        this.mng = mng;

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeByte(CONNECT_QUERY);
        dos.writeUTF(myName);

        startThread();
    }


    private void startThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    tryReadMsg();
                } catch (IOException e) {
                    mng.processDisconnected();
                    return;
                }
            }
        });
        thread.start();
    }

    private void tryReadMsg() throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        while (!socket.isClosed()) {
            int operation = dis.readByte();
            if (operation == MSG_QUERY) {
                handleMsgQuery();
            } else if (operation == CONNECT_QUERY) {
                handleConnectQuery();
            } else {
                LOG.warn("Wrong query " + String.format("%x", operation));
            }
        }
    }

    private void handleConnectQuery() throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        hisName = dis.readUTF();
    }

    private void handleMsgQuery() throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String text = dis.readUTF();

        mng.processNewMessage(new Message(hisName, text));
    }

    public void sendMsg(String text) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeByte(MSG_QUERY);
            dos.writeUTF(text);
            mng.processNewMessage(new Message(myName, text));
        } catch (IOException e) {
            LOG.trace(e.getMessage());
            mng.processDisconnected();
        }
    }
}
