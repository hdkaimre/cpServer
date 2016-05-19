import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by HansDaniel on 17.05.2016.
 */

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1337);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                Runnable runnable = new ConnectionHandler(socket);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }
        finally {
            serverSocket.close();
        }
    }
}