package uae.mesbahi.houda.ips.services;

import uae.mesbahi.houda.ips.interfaces.Filter;
import uae.mesbahi.houda.ips.models.Image;

import java.awt.*;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import static uae.mesbahi.houda.ips.services.GrayScale.getGrayScale;

public class FilterApplier extends UnicastRemoteObject implements Filter {
    private final Logger logger = Logger.getLogger(FilterApplier.class.getName());

    int[][] gx;
    int[][] gy;
    String name;

    public FilterApplier(int[][] gx, int[][] gy, String name) throws IOException {
        super();
        this.gx = gx;
        this.gy = gy;
        this.name = name;
    }

    @Override
    public byte[] apply(byte[] bytesImage) throws IOException {
        logger.info("Received image to apply filter " + name);
        Image image = new Image(bytesImage);
        int height = image.getHeight();
        int width = image.getWidth();
        int maxGrad = -1;
        int[][] edges = new int[width][height];

        int[][] grayPixels = getGrayScale(image);

        logger.info("Applying filter " + name);
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int x = 0;
                int y = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        x += gy[k + 1][l + 1] * grayPixels[i + k][j + l];
                        y += gx[k + 1][l + 1] * grayPixels[i + k][j + l];
                    }
                }
                int grad = (int) Math.sqrt(x * x + y * y);
                if (grad > maxGrad) {
                    maxGrad = grad;
                }

                edges[i][j] = grad;
            }
        }

        // Normalize the image
        double scale = 255.0 / maxGrad;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int edgeC = (int) (edges[x][y] * scale);
                image.setPixel(x, y, new Color(edgeC, edgeC, edgeC).getRGB());
            }
        }

        logger.info("Filter " + name + " applied successfully");
        return image.toByteArray();
    }
}
