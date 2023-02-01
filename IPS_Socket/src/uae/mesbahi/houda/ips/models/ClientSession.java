package uae.mesbahi.houda.ips.models;

import uae.mesbahi.houda.ips.enums.*;
import uae.mesbahi.houda.ips.interfaces.*;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ClientSession extends Thread {
    private final Connection conn;
    private final static String packagePath = "uae.mesbahi.houda.ips.services";

    public ClientSession(Connection conn) {
        this.conn = conn;
    }


    private ClientRequest requestChecker(Object request) throws IOException {
        if (request instanceof ClientRequest)
            return (ClientRequest) request;

        return new EmptyRequest();
    }

    Image execute(ClientRequest rqst) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        FilterType filterType = rqst.getFilterType();
        byte[] image = rqst.getImage();
        if (filterType != FilterType.NONE && filterType != FilterType.DEFAULT) {
            Filter filter = (Filter) Class.forName("uae.mesbahi.houda.ips.services." + filterType.name())
                    .getDeclaredConstructor()
                    .newInstance();
            return filter.apply(new Image(image));
        }
        return new Image();

    }

    @Override
    public void run() {
        ClientRequest request = null;
        try {
            request = new EmptyRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                assert request != null;
                if (request.isQuit()) break;
                request = requestChecker(conn.receive());
                Image img = execute(request);
                conn.send(new ServerResponse(img.toByteArray()));
            }
        } catch (EOFException ignored) {

        } catch (Exception e) {
            try {
                conn.shutdown();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
