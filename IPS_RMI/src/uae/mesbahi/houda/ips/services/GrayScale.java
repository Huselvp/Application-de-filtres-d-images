package uae.mesbahi.houda.ips.services;


import uae.mesbahi.houda.ips.models.Image;

public class GrayScale {
    public static int[][] getGrayScale(Image image) {
        int[][] gray = null;
        int height = image.getHeight();
        int width = image.getWidth();

        if (height > 0 && width > 0) {
            gray = new int[width][height];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int rgb = image.getPixel(i, j);
                    long rgbGray = Math.round((jred(rgb) + jgreen(rgb) + jblue(rgb)) / 3.0);
                    gray[i][j] = (int)rgbGray;
                }
            }
        }
        return gray;
    }

    public static int jrgb(int r, int g, int b) { return (r << 16) + (g << 8) + b; }

    public static int jred(int rgb)   { return ((rgb >> 16) & 0xff); }
    public static int jgreen(int rgb) { return ((rgb >> 8) & 0xff); }
    public static int jblue(int rgb)  { return (rgb & 0xff); }
}
