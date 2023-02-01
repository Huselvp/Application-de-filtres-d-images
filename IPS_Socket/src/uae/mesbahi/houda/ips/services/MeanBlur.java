package uae.mesbahi.houda.ips.services;

import uae.mesbahi.houda.ips.interfaces.Filter;
import uae.mesbahi.houda.ips.models.Image;

public class MeanBlur implements Filter {
    
    @Override
    public Image apply(Image img) {

        int maxHeight = img.getHeight();
        int maxWidth = img.getWidth();

        int[][] newPixels = new int [maxHeight][maxWidth];

        for (int v=1; v < maxHeight-1; v++) {
            for (int u=1; u < maxWidth-1; u++) {

                int sumr = 0, sumg = 0, sumb = 0;
                for (int j=-1; j<=1; j++) {
                    for (int i=-1; i<=1; i++) {
                        int pixel = img.getPixel(u + i, v + j);
                        int rr = (pixel & 0x00ff0000) >> 16, rg = (pixel & 0x0000ff00) >> 8, rb = pixel & 0x000000ff;
                        sumr += rr;
                        sumg += rg;
                        sumb += rb;
                    }
                }

                sumr/=9; sumg/=9; sumb/=9;
                newPixels[v][u] =(sumr<<16)|(sumg<<8)|(0xff000000|sumb);
            }
        }

        for(int x = 1; x < maxWidth; x++){
            for(int y = 1; y < maxHeight; y++){
                img.setPixel(x, y, newPixels[y][x]);
            }
        }

        return img;
    }
}
