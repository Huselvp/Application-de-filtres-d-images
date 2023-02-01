package uae.mesbahi.houda.ips.services;

import java.io.IOException;

public class Sobel extends FilterApplier {

    public Sobel() throws IOException {
        super(new int[][] {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}},
                new int[][] {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}},
                "Sobel");
    }
}
