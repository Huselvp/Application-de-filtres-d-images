package uae.mesbahi.houda.ips;

import uae.mesbahi.houda.ips.models.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class SocketServer {
    private final static int PORT = 8949;
    private final static String HOST = "localhost";
    private static final Logger log = Logger.getLogger(SocketServer.class.getName());

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT, 50, java.net.InetAddress.getByName(HOST));
        server.setReuseAddress(true);
        log.info("Server started on: " + HOST + ":" + PORT);

        while (true) {
            try {
                Connection conn = new Connection(server);
                new ClientSession(conn).start();
            } catch (IOException e) {
                server.close();
                break;
            }
        }
    }
}
