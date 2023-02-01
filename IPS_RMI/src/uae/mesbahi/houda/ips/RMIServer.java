package uae.mesbahi.houda.ips;

import uae.mesbahi.houda.ips.controllers.Controller;
import uae.mesbahi.houda.ips.services.Laplace;
import uae.mesbahi.houda.ips.services.Prewitt;
import uae.mesbahi.houda.ips.services.Sobel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.logging.Logger;

public class RMIServer {

    static Logger logger = Logger.getLogger(RMIServer.class.getName());

    public static void main(String[] args) {
        try {
            int port = 1099;
            LocateRegistry.createRegistry(port);
            initiateControllers();
            logger.info("Server is ready");
        } catch (Exception e) {
            logger.info("Server is not ready: \n" + e);
            System.exit(-1);
        }
    }

    private static void initiateControllers() throws IOException {
        List.of(new Controller(new Prewitt()),
                new Controller(new Sobel()),
                new Controller(new Laplace()))
                .forEach(controller -> {
                    try {
                        controller.bindFilter();
                    } catch (RemoteException | MalformedURLException e) {
                        logger.info("Controller is not ready to bind: \n" + e);
                    }
                });
    }
}
