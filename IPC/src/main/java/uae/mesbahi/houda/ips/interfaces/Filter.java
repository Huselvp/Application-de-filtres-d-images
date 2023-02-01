package uae.mesbahi.houda.ips.interfaces;

import uae.mesbahi.houda.ips.models.Image;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Filter extends Remote {
    byte[] apply(byte[] bytesImage) throws RemoteException;
    Image apply(Image image);
}
