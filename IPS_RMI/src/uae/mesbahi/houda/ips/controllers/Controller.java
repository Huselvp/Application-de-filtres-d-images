package uae.mesbahi.houda.ips.controllers;

import uae.mesbahi.houda.ips.interfaces.Filter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.logging.Logger;

public class Controller {
    Logger logger = Logger.getLogger(Controller.class.getName());

    String rmi = "rmi://localhost:1099/";
    Filter filter;

    public Controller(Filter filter) {
        this.filter = filter;
    }

    public void bindFilter() throws RemoteException, MalformedURLException {
        String packageName = filter.getClass().getTypeName().toLowerCase(Locale.ROOT);
        String className = packageName.substring(packageName.lastIndexOf('.') + 1);
        logger.info("Binding Filter at endpoint: " + rmi + className);
        Naming.rebind(rmi + className, filter);
    }
}
