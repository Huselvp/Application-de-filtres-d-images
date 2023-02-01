package uae.mesbahi.houda.ips.models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Image implements Serializable {
    private final BufferedImage image;

    public Image(String filePath) throws IOException {
        this.image = ImageIO.read(new File(filePath));
    }

    public Image(byte[] imageData) throws IOException {
        this.image = ImageIO.read(new ByteArrayInputStream(imageData));
    }

    public void write(File outputFile) throws IOException {
        ImageIO.write(image, "png", outputFile);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        return outputStream.toByteArray();
    }

    public javafx.scene.image.Image toFXImage() throws IOException {
        return new javafx.scene.image.Image(new ByteArrayInputStream(toByteArray()));
    }
}
