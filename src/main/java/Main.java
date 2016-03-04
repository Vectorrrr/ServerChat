import server.Server;

import static java.lang.Thread.sleep;

/**
 * Created by igladush on 04.03.16.
 */
public class Main {
    public static void main(String[] args){
        Server server=new Server();
        server.runServer();
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
