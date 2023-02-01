package uae.mesbahi.houda.ips.models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Image implements Serializable {
    private final BufferedImage image;

    public Image() {
        this.image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }

    public Image(byte[] imageData) throws IOException {
        this.image = ImageIO.read(new ByteArrayInputStream(imageData));
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getPixel(int x, int y) {
        return image.getRGB(x, y);
    }

    public void setPixel(int x, int y, int edgeC) {
        Color c = new Color(edgeC);
        image.setRGB(x, y, c.getRGB());
    }

    public void write(File outputFile) throws IOException {
        ImageIO.write(image, "png", outputFile);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        return outputStream.toByteArray();
    }

    public int[][] getPixels() {
        int[][] pixels = new int[image.getWidth()][image.getHeight()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                pixels[i][j] = image.getRGB(i, j);
            }
        }
        return pixels;
    }
}
