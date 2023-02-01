package uae.mesbahi.houda.ips.models;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    private final byte[] imageBytes;

    public ServerResponse(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
