package server;

import model.Message;

import java.util.Iterator;

/**
 * Created by igladush on 04.03.16.
 */
public class SenderMessages extends Thread {
    private Server server;
    private boolean working = true;

    public SenderMessages(Server server) {
        this.server = server;
    }

    /**
     * While work this method he send message every client in the chat
     * besides author this message
     */
    public void run() {
        while (working) {
            if (server.getCountMessage() > 0) {
                Message m = server.popFirstMessage();

                Iterator<Compound> it = server.getAllUsers();
                while (it.hasNext()) {
                    Compound compound = it.next();
                    if (m.getIdAuthor() != compound.getIdCompound()) {
                        compound.send(m.getText());
                    }
                }
            }
        }
    }

}
