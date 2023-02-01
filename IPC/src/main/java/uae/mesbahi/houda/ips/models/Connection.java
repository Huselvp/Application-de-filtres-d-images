package uae.mesbahi.houda.ips.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;
    protected Socket socket;

    public Connection(String host, int port) {
        try {
            socket = new Socket(host, port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerResponse receive() throws IOException, ClassNotFoundException {
        Object obj = ois.readObject();
        if (obj instanceof ServerResponse)
            return (ServerResponse) obj;
        return null;
    }

    public void send(ClientRequest msg) throws IOException {
        oos.writeObject(msg);
    }

    public void shutdown() throws IOException {
        ois.close();
        oos.close();
        socket.close();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }
}
