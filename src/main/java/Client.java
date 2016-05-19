import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
/**
 * Created by HansDaniel on 17.05.2016.
 */


public class Client {
    public static void main(String[] args) throws IOException {

        String outputDir;
        String fileName;


        if (args.length == 2) {
            fileName = args[0];
            outputDir = args[1];
        } else {
            throw new IllegalArgumentException();
        }
        try (   Socket socket = new Socket(InetAddress.getLocalHost(), 1337);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            File outputFile = new File(outputDir);
            FileOutputStream os = new FileOutputStream(outputFile);
            dos.writeUTF(fileName);

            byte[] buf = new byte[1024];
            int length;

            int size = dis.readInt();
            while((length = dis.read(buf)) > 0) {
                os.write(buf, 0, length);
            }

            System.out.println("Faili suurus on " + (double)size / 1000000 + " megabaiti");
        }
    }
}