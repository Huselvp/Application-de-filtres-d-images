package uae.mesbahi.houda.ips.service;

import uae.mesbahi.houda.ips.interfaces.Filter;
import uae.mesbahi.houda.ips.models.Image;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class EdgeDetectionClientService {

    private final String rmiPath = "rmi://localhost:1099/";

    public byte[] applySobel(Image image) throws IOException, NotBoundException {
        Filter stub = (Filter) Naming.lookup(rmiPath + "sobel");
        return stub.apply(image.toByteArray());
    }

    public byte[] applyPrewitt(Image image) throws IOException, NotBoundException {
        Filter stub = (Filter) Naming.lookup(rmiPath + "prewitt");
        return stub.apply(image.toByteArray());
    }

    public byte[] applyLaplace(Image image) throws IOException, NotBoundException {
        Filter stub = (Filter) Naming.lookup(rmiPath + "laplace");
        return stub.apply(image.toByteArray());
    }
}
