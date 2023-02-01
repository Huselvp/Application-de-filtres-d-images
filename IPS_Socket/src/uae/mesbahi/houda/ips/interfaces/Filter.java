package uae.mesbahi.houda.ips.interfaces;

import uae.mesbahi.houda.ips.models.Image;

public interface Filter {
    Image apply(Image image);
}
