package uae.mesbahi.houda.ips.service;

import uae.mesbahi.houda.ips.enums.FilterType;
import uae.mesbahi.houda.ips.models.ClientRequest;
import uae.mesbahi.houda.ips.models.Connection;
import uae.mesbahi.houda.ips.models.Image;
import uae.mesbahi.houda.ips.models.ServerResponse;

import java.io.IOException;

public class BlurClient extends Thread {

    private final static int PORT = 8949;
    private final static String HOST = "localhost";

    private Connection conn = new Connection(HOST, PORT);
    private Image image;
    private boolean updated = false;

    public void setImage(Image image) {
        this.image = image;
    }

    public Image applyGaussianBlur(Image img) {
        return applyFilter(img, FilterType.GaussianBlur);
    }

    public Image applyMedianBlur(Image img) {
        return applyFilter(img, FilterType.MedianBlur);
    }

    public Image applyMeanBlur(Image image) {
        return applyFilter(image, FilterType.MeanBlur);
    }

    private Image applyFilter(Image img, FilterType filterType) {
        updated = false;
        if (conn == null || conn.isClosed())
            conn = new Connection(HOST, PORT);

        try {
            ClientRequest request = new ClientRequest(img.toByteArray(), filterType);
            conn.send(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();

        while (!updated) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return image;
    }

    @Override
    public void run() {
        while (!updated) {
            try {
                ServerResponse resp = conn.receive();
                if (resp != null) {
                    image = new Image(resp.getImageBytes());
                    updated = true;
                }
            } catch (IOException | ClassNotFoundException e) {
                try {
                    conn.shutdown();
                    conn = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            conn.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
