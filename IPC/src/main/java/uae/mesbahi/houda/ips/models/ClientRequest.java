package uae.mesbahi.houda.ips.models;

import uae.mesbahi.houda.ips.enums.FilterType;

import java.io.Serializable;


public class ClientRequest implements Serializable {

    private final byte[] image;
    private final FilterType filterType;

    public ClientRequest(byte[] image, FilterType filterType) {
        this.image = image;
        this.filterType = filterType;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public boolean isQuit() {
        return filterType == FilterType.NONE;
    }

    public byte[] getImage() {
        return image;
    }
}
