import java.io.*;
import java.net.Socket;
/**
 * Created by HansDaniel on 17.05.2016.
 */


public class ConnectionHandler implements Runnable {
    Socket socket;

    ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        ) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String fileName = dis.readUTF();
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

            int length;
            byte[] buf = new byte[1024];


            while((length = is.read(buf)) > 0) {
                byteArrayOutputStream.write(buf, 0, length);
            }

            dos.writeInt(byteArrayOutputStream.size());
            dos.write(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
