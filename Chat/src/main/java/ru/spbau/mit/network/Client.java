package ru.spbau.mit.network;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.Logger;
import ru.spbau.mit.Message;
import ru.spbau.mit.MessageGRPC;
import ru.spbau.mit.MessengerGrpc;
import ru.spbau.mit.control.GUIManager;

import java.io.IOException;

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
            ManagedChannelBuilder<?> channelBuilder =
                    ManagedChannelBuilder.forAddress(clientHost, clientPort).usePlaintext(true);
            ManagedChannel channel = channelBuilder.build();
            MessengerGrpc.MessengerStub asyncStub = MessengerGrpc.newStub(channel);


            StreamObserver<MessageGRPC> observer =
                    asyncStub.chat(new StreamObserver<MessageGRPC>() {
                        String hisName;

                        @Override
                        public void onNext(MessageGRPC message) {
                            if (message.getType() == MessageGRPC.MessageType.CONNECT_QUERY) {
                                hisName = message.getData();
                            } else if (message.getType() == MessageGRPC.MessageType.MSG_QUERY) {
                                mng.processNewMessage(new Message(hisName, message.getData()));
                            } else {
                                mng.processTyping();
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
                    });
            try {
                Connection connect = new Connection(observer, clientName, mng);
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