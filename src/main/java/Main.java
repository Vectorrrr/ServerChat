import server.Server;

import java.util.Scanner;

/**
 * Created by igladush on 04.03.16.
 */
public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Input port for server");
        int id=sc.nextInt();

        Server server=new Server(id);
        System.out.println("Server starts....");
        server.start();

    }
}
