package ru.spbau.mit.network;

import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import ru.spbau.mit.Message;
import ru.spbau.mit.MessageGRPC;
import ru.spbau.mit.MessengerGrpc;
import ru.spbau.mit.control.GUIManager;

import java.io.IOException;

/**
 * create connection from server part
 */
public class Server implements ConnectionCreator {
    private static final Logger LOG = Logger.getLogger(Server.class);

    private Integer port;
    private String name;

    private StreamObserver<MessageGRPC> observer;

    private io.grpc.Server server;

    public Server(String name, Integer port) throws IOException {
        this.name = name;
        this.port = port;
    }

    @Override
    public void connect(GUIManager mng) throws IOException {
        Thread connectThread = new Thread(() -> {
            try {
                server = ServerBuilder.forPort(port)
                        .addService(new MessageHandler(mng))
                        .build()
                        .start();
                server.awaitTermination();
            } catch (InterruptedException | IOException e) {
                LOG.warn(e.getMessage());
            }
        });

        connectThread.start();
    }


    private class MessageHandler extends MessengerGrpc.MessengerImplBase {
        GUIManager mng;

        MessageHandler(GUIManager mng) {
            this.mng = mng;
        }

        @Override
        public StreamObserver<MessageGRPC> chat(StreamObserver<MessageGRPC> responseObserver) {
            if (observer == null) {
                observer = responseObserver;
                try {
                    Connection connect = new Connection(observer, name, mng);
                    mng.processConnection(connect);
                } catch (IOException e) {
                    mng.processDisconnected();
                }

                return new StreamObserver<MessageGRPC>() {
                    String hisName;

                    @Override
                    public void onNext(MessageGRPC message) {
                        if (message.getType() == MessageGRPC.MessageType.CONNECT_QUERY) {
                            hisName = message.getData();
                        } else if (message.getType() == MessageGRPC.MessageType.MSG_QUERY) {
                            mng.processNewMessage(new Message(hisName, message.getData()));
                        }
                    }

                    @Override
                    public void onError(Throwable exception) {
                        LOG.warn(exception.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        mng.processDisconnected();
                    }
                };
            }
            LOG.trace("Second try to chat. Server already have chat");
            return null;
        }
    }

    @Override
    public void cancel() {
        server.shutdown();
    }
}
