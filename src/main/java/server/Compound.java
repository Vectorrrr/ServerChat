package server;

import model.Message;

import java.io.*;
import java.net.Socket;

/**
 * Created by igladush on 04.03.16.
 */
public class Compound extends Thread {
    private final String ERROR_STREAM = "When I create stream I have error!";
    private final String ERROR_READ_OR_WRITE = "When I read or write I have error";
    private final String ERROR_CLOSE = "When I close stream reader I have ERROR";
    private final String EXIT = "Buy";
    private DataInputStream reader;
    private DataOutputStream writer;


    private int number;

    public Compound(Socket socket, int Id) {
        this.number = Id;
        try {
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(ERROR_STREAM);
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        read();
        closeStream();
        Server.users.remove(this);
        System.out.println("I closed the clint number" + this.number);
    }

    private void closeStream() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(ERROR_CLOSE);
            e.printStackTrace();
        }
    }

    private void read() {
        String message;
        while (true) {
            try {
                message = reader.readUTF();
                System.out.println(message);

                if (EXIT.equals(message)) {
                    writer.writeUTF("1234567890");
                    writer.flush();

                    break;
                }
                Server.allMessage.add(new Message(message, number));
            } catch (IOException e) {
                System.out.println(ERROR_READ_OR_WRITE);
                e.printStackTrace();
            }
        }


    }

    public void send(String s) {
        //todo constant
        try {
            writer.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumber() {
        return this.number;
    }

}
