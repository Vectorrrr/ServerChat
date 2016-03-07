package server;

import model.Message;

import java.io.*;
import java.net.Socket;

/**
 * Created by igladush on 04.03.16.
 */

/**
 * Class create for sending and reception string by one compound
 * */
public class Compound extends Thread {
    private final String ERROR_READ_OR_WRITE = "When I read or write I have error";
    private final String ERROR_CLOSE_READER = "When I close stream reader I have ERROR";
    private final String ERROR_CLOSE_WRITER = "When I close stream writer I have ERROR";
    private final String EXIT = "Buy";
    private DataInputStream reader;
    private DataOutputStream writer;
    private Socket socket;
    private Server server;
    private int idCompound;

    public int getIdCompound() {
        return this.idCompound;
    }

    public Compound(Socket socket, int Id, Server server) {
        this.socket = socket;
        this.idCompound = Id;
        this.server = server;
    }


    @Override
    public void run() {
        createStreams();
        read();
        closeAllStreams();
        server.removeCompound(this);
        System.out.println("I closed the clint " + this.idCompound);
    }

    private void createStreams() {
        try {
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //methods that close all stream
    private void closeAllStreams() {
        closeReader();
        closeWriter();
    }

    private void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new IllegalArgumentException(ERROR_CLOSE_WRITER);
                }
            }
        }
    }

    private void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new IllegalArgumentException(ERROR_CLOSE_READER);
                }
            }
        }
    }

    /**
     * this method create for read all message from one compound
     * when co,pound send exit word, this method send secret word for client
     * this word signals client, that server stopped this socket correct
     */
    private void read() {
        String message;
        while (true) {
            try {
                message = reader.readUTF();
                System.out.println(message);

                if (EXIT.equals(message)) {
                    try (DataOutputStream writer = new DataOutputStream(socket.getOutputStream())) {
                        writer.writeUTF("1234567890");
                        writer.flush();

                    } catch (IOException e) {
                        System.out.println(ERROR_CLOSE_READER);
                    }
                    break;
                }

                server.addMessage(new Message(message, idCompound));
            } catch (IOException e) {
                System.out.println(ERROR_READ_OR_WRITE);
                e.printStackTrace();
            }
        }
    }

    //todo send message this user/ I don't know that good use every sending create writer?

    /**
     * This method send some string for this Compound
     */
    public void send(String s) {
        try {
            writer.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
