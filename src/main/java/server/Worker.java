package server;

import model.Message;

import static java.lang.Thread.sleep;

/**
 * Created by igladush on 04.03.16.
 */
public class Worker implements Runnable {

    public void run() {
        while (true) {
            if (Server.allMessage.size() > 0) {
                Message m = Server.allMessage.pop();

                for (Compound compound : Server.users) {
                    if (m.getIdAuthor() != compound.getNumber()) {
                        compound.send(m.getText());
                    }
                }

            }

        }
    }

}
