package server;

import model.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by igladush on 04.03.16.
 */
public class Server {
    private final String ERROR_SOCKET_INIT = "I can't create socket";
    private final String ERROR_SOCKET_ACCEPT = "When I want wait error I have exception";

    static ConcurrentLinkedDeque<Message> allMessage = new ConcurrentLinkedDeque<Message>();
    static ConcurrentLinkedDeque<Compound> users = new ConcurrentLinkedDeque<Compound>();
    private int idNextUser = 0;

    public void runServer() {
        int port = 7997;
        new Thread(new Worker()).start();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(ERROR_SOCKET_INIT);
        }

        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Compound comp = new Compound(socket, idNextUser);
                users.add(comp);
                comp.start();
                System.out.println("add new users he has id" + idNextUser++);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(ERROR_SOCKET_ACCEPT);
            }
        }

    }
}
