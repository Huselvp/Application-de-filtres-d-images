package uae.mesbahi.houda.ips.services;

import java.io.IOException;

public class Prewitt extends FilterApplier {

    public Prewitt() throws IOException {
        super(new int[][]{{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}},
                new int[][]{{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}},
                "Prewitt");
    }
}
