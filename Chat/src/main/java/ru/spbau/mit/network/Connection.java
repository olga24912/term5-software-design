package ru.spbau.mit.network;

import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import ru.spbau.mit.Message;
import ru.spbau.mit.MessageGRPC;
import ru.spbau.mit.control.GUIManager;

import java.io.IOException;

/**
 * Class for sending messages in current connection.
 */
public class Connection {
    private StreamObserver<MessageGRPC> messageStreamObserver;
    private static final Logger LOG = Logger.getLogger(Connection.class);

    private String myName;

    private GUIManager mng;

    public Connection(StreamObserver<MessageGRPC> messageStreamObserver, String myName, GUIManager mng) throws IOException {
        this.messageStreamObserver = messageStreamObserver;
        this.myName = myName;
        this.mng = mng;


        messageStreamObserver.onNext(MessageGRPC.newBuilder().
                setType(MessageGRPC.MessageType.CONNECT_QUERY).setData(myName).build());
    }

    public void sendMsg(String text) {
        messageStreamObserver.onNext(MessageGRPC.newBuilder().
                setType(MessageGRPC.MessageType.MSG_QUERY).setData(text).build());
        mng.processNewMessage(new Message(myName, text));
    }
}
