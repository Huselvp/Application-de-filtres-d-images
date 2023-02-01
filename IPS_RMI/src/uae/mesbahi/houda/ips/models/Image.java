package uae.mesbahi.houda.ips.models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Image {
    private final BufferedImage image;

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
        image.setRGB(x, y, edgeC);
    }

    public void write(File outputFile) throws IOException {
        ImageIO.write(image, "png", outputFile);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        return outputStream.toByteArray();
    }
}
