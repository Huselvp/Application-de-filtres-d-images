package uae.mesbahi.houda.ips.services;

import uae.mesbahi.houda.ips.interfaces.Filter;
import uae.mesbahi.houda.ips.models.Image;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class Laplace extends UnicastRemoteObject implements Filter {
    private final Logger logger = Logger.getLogger(FilterApplier.class.getName());

    public Laplace() throws RemoteException {
        super();
    }

    @Override
    public byte[] apply(byte[] bytesImage) throws IOException {
        logger.info("Received image to apply Laplace filter");
        Image img = new Image(bytesImage);
        Image copy = new Image(bytesImage);
        int width = img.getWidth();
        int height = img.getHeight();

        int[][] grayScale = GrayScale.getGrayScale(img);

        int sum, a;

        logger.info("Applying Laplace filter");
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                sum = (-1 * (grayScale[x - 1][y - 1] & 0xff))
                        + (-1 * (grayScale[x][y - 1] & 0xff))
                        + (-1 * (grayScale[x + 1][y - 1] & 0xff))
                        + (-1 * (grayScale[x - 1][y] & 0xff))
                        + (8 * (grayScale[x][y] & 0xff))
                        + (-1 * (grayScale[x + 1][y] & 0xff))
                        + (-1 * (grayScale[x - 1][y + 1] & 0xff))
                        + (-1 * (grayScale[x][y + 1] & 0xff))
                        + (-1 * (grayScale[x + 1][y + 1] & 0xff));
                sum = Math.max(sum, 0);
                a = ((grayScale[x][y] >> 24) & 0xff);
                copy.setPixel(x, y, ((a << 24) | (sum << 16) | (sum << 8) | (sum)));
            }
        }

        logger.info("Applying Laplace filter finished");
        logger.info("Sending image back");
        return copy.toByteArray();
    }
}
