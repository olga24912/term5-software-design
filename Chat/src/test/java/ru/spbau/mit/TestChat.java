package ru.spbau.mit;

import org.junit.Test;
import ru.spbau.mit.control.GUIManager;
import ru.spbau.mit.network.Client;
import ru.spbau.mit.network.Connection;
import ru.spbau.mit.network.Server;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestChat {
    private static final int SLEEP_TIME = 500;

    @Test
    public void testSend() throws IOException, InterruptedException {
        final Message[] msgs = new Message[2];
        final Connection[] connect = new Connection[2];

        GUIManager mngClient = new GUIManager() {
            @Override
            public void processNewMessage(Message msg) {
                msgs[0] = msg;
            }

            @Override
            public void processTyping() {
            }

            @Override
            public void processConnection(Connection connection) {
                connect[0] = connection;
            }

            @Override
            public void processDisconnected() {

            }
        };
        GUIManager mngServer = new GUIManager() {
            @Override
            public void processNewMessage(Message msg) {
                msgs[1] = msg;
            }

            @Override
            public void processTyping() {
            }

            @Override
            public void processConnection(Connection connection) {
                connect[1] = connection;
            }

            @Override
            public void processDisconnected() {

            }
        };

        Server server = new Server("Pavel", 3000);
        server.connect(mngServer);

        Client client = new Client("Olga", "localhost", 3000);
        client.connect(mngClient);

        Thread.sleep(SLEEP_TIME);

        connect[1].sendMsg("hello!");

        Thread.sleep(SLEEP_TIME);

        assertEquals(msgs[0].getMsg(), "hello!");
        assertEquals(msgs[0].getName(), "Pavel");

        Thread.sleep(SLEEP_TIME);

        connect[0].sendMsg("I love you!");

        Thread.sleep(SLEEP_TIME);

        assertEquals(msgs[1].getMsg(), "I love you!");
        assertEquals(msgs[1].getName(), "Olga");
    }
}
