package uae.mesbahi.houda.ips.services;

import uae.mesbahi.houda.ips.interfaces.Filter;
import uae.mesbahi.houda.ips.models.Image;

import java.awt.*;
import java.util.Arrays;

public class MedianBlur implements Filter {

    @Override
    public Image apply(Image image) {
        Color[] pixel=new Color[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
                pixel[0] = new Color(image.getPixel(i - 1, j - 1));
                pixel[1] = new Color(image.getPixel(i - 1, j));
                pixel[2] = new Color(image.getPixel(i - 1, j + 1));
                pixel[3] = new Color(image.getPixel(i, j + 1));
                pixel[4] = new Color(image.getPixel(i + 1, j + 1));
                pixel[5] = new Color(image.getPixel(i + 1, j));
                pixel[6] = new Color(image.getPixel(i + 1, j - 1));
                pixel[7] = new Color(image.getPixel(i, j - 1));
                pixel[8] = new Color(image.getPixel(i, j));
                for (int k = 0; k < 9; k++) {
                    R[k] = pixel[k].getRed();
                    B[k] = pixel[k].getBlue();
                    G[k] = pixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                image.setPixel(i, j, new Color(R[4], G[4], B[4]).getRGB());
            }
        }

        return image;
    }
}
