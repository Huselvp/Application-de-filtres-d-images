package uae.mesbahi.houda.ips.models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;
    protected Socket socket;

    public Connection(ServerSocket serverSocket) throws IOException {
        this.socket = serverSocket.accept();
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public ClientRequest receive() throws ClassNotFoundException, IOException {
        Object obj = ois.readObject();
        if (obj instanceof ClientRequest)
            return (ClientRequest) obj;
        return null;
    }

    public void send(ServerResponse response) throws IOException {
        oos.writeObject(response);
    }

    public void shutdown() throws IOException {
        ois.close();
        oos.close();
        socket.close();
    }
}