package uae.mesbahi.houda.ips.models;

import uae.mesbahi.houda.ips.enums.FilterType;

import java.io.IOException;

public class EmptyRequest extends ClientRequest {
    EmptyRequest() throws IOException {
        super(new Image().toByteArray(), FilterType.DEFAULT);
    }
}
