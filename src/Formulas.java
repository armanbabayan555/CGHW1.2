import java.awt.*;
import java.awt.image.BufferedImage;

public class Formulas {
    public static float[][] getTranslationMatrix(int tx, int ty) {
        return new float[][]{{1, 0, tx}, {0, 1, ty}, {0, 0, 1}};
    }

    public static float[][] getRotationMatrix(float angle) {
        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        return new float[][]{{(float) cos, (float) -sin, 0},
                {(float) sin, (float) cos, 0},
                {0, 0, 1}};
    }

    public static float[][] getRotationMatrix(int cx, int cy, float angle) {
        float[][] productMatrix = matrixMultiplication(getTranslationMatrix(cx, cy),
                getRotationMatrix(angle));
        return matrixMultiplication(productMatrix, getTranslationMatrix(-cx, -cy));

    }

    public static float[][] getScalingMatrix(int cx, int cy, float sx, float sy) {
        return new float[][]{{sx, 0, cx * (1 - sx)}, {0, sy, cy * (1 - sy)}, {0, 0, 1}};
    }

    public static float[][] matrixMultiplication(float[][] m1, float[][] m2) throws IllegalArgumentException {
        float[][] newMatrix = new float[m1.length][m2[0].length];

        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    newMatrix[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return newMatrix;

    }

    public static void bresenham(int x1, int y1, int x2, int y2, BufferedImage img, Graphics g) {
        int x, y, e, incX, incY, inc1, inc2;
        int dx = x2 - x1;
        int dy = y2 - y1;
        if (dx < 0) {
            dx = -dx;
        }
        if (dy < 0) {
            dy = -dy;
        }
        if (x2 < x1) {
            incX = -1;
        } else {
            incX = 1;
        }
        if (y2 < y1) {
            incY = -1;
        } else {
            incY = 1;
        }
        x = x1;
        y = y1;
        if (dx > dy) {
            putPixel(img, g, x, y);
            e = 2 * dy - dx;
            inc1 = 2 * (dy - dx);
            inc2 = 2 * dy;
            for (int j = 0; j < dx; j++) {
                if (e >= 0) {
                    y += incY;
                    e += inc1;
                } else {
                    e += inc2;
                }
                x += incX;
                putPixel(img, g, x, y);
            }
        } else {
            putPixel(img, g, x, y);
            e = 2 * dx - dy;
            inc1 = 2 * (dx - dy);
            inc2 = 2 * dx;
            for (int j = 0; j < dy; j++) {
                if (e >= 0) {
                    x += incX;
                    e += inc1;
                } else {
                    e += inc2;
                }
                y += incY;
                putPixel(img, g, x, y);
            }
        }
    }

    public static void putPixel(BufferedImage img, Graphics g, int x, int y) {
        int[] pixel = {255, 255, 255};
        img.getRaster().setPixel(x, y, pixel);
        g.drawImage(img, 0, 0, (img1, infoflags, xx, yy, width, height) -> false);
    }


}
