package uae.mesbahi.houda.ips.interfaces;

import java.io.IOException;
import java.rmi.Remote;

public interface Filter extends Remote {
    byte[] apply(byte[] bytesImage) throws IOException;
}
