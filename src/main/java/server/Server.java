package server;

import model.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by igladush on 04.03.16.
 */
//Class for add client server users and that class keep list all users and messages
public class Server  extends Thread {
    private final String ERROR_SOCKET_INIT = "I can't create socket";
    private final String ERROR_SOCKET_ACCEPT = "When I want wait error I have exception";
    private int port;
    private ConcurrentLinkedDeque<Message> allMessage = new ConcurrentLinkedDeque<Message>();
    private ConcurrentLinkedDeque<Compound> users = new ConcurrentLinkedDeque<Compound>();
    private int idNextUser = 0;

    public Server(int port) {
        this.port = port;
    }


    //Method create server socket and listening port if it has new compound that add this compound
    //to all users list
    @Override
    public void run() {

        SenderMessages senderMessages = new SenderMessages(this);
        senderMessages.start();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(ERROR_SOCKET_INIT);
        }

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Compound comp = new Compound(socket, idNextUser, this);
                users.add(comp);
                comp.start();
                System.out.println("add new users he has id" + idNextUser++);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(ERROR_SOCKET_ACCEPT);
            }
        }


    }

    public void removeCompound(Compound compound) {
        users.remove(compound);
    }

    public int getCountMessage() {
        return this.allMessage.size();
    }

    public Message popFirstMessage() {
        return allMessage.pop();
    }

    public Iterator<Compound> getAllUsers() {
        return users.iterator();
    }

    public void addMessage(Message message) {
        allMessage.add(message);
    }
}
