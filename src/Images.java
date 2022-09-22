import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Images {
    public static Graphics g;
    public static BufferedImage img;
    public static final Polygon polygon = new Polygon();
    private static boolean drawPolygon = false;


    public static BufferedImage gradientSetRaster(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        WritableRaster raster = img.getRaster();
        int[] pixel = new int[3]; //RGB

        for (int y = 0; y < height; y++) {
            int val = (int) (y * 255f / height);
            for (int shift = 1; shift < 3; shift++) {
                pixel[shift] = val;
            }

            for (int x = 0; x < width; x++) {
                raster.setPixel(x, y, pixel);
            }
        }

        return img;
    }

    public static void main(String... args) {
        Frame w = new Frame("Raster");  //window
        final int imageWidth = 500;
        final int imageHeight = 500;

        w.setSize(imageWidth, imageHeight);
        w.setLocation(100, 100);
        w.setVisible(true);

        g = w.getGraphics();

        img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        gradientSetRaster(img);
        g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> false);  //draw the image. You can think of this as the display method.
        final int[] pixel = {255, 255, 255};


        w.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (drawPolygon) {
                        polygon.clearPoints();
                        drawPolygon = false;
                        g.drawImage(img, 0, 0, (img1, infoFlags, x, y, width, height) -> false);
                    }
                    polygon.addPoint(new Point(e.getX(), e.getY(), 1));
                    polygon.connect(img, g);
                    img.getRaster().setPixel(e.getX(), e.getY(), pixel);
                    g.drawImage(img, 0, 0, (img1, infoFlags, x, y, width, height) -> false);
                }
            }
        });

        w.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gradientSetRaster(img);
                if (e.getKeyCode() == 32) { // USE SPACE TO FORM POLYGON
                    polygon.connectFirstAndLast(img, g);
                    drawPolygon = true;
                    g.drawImage(img, 0, 0, (img1, infoFlags, x, y, width, height) -> false);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    polygon.translate(0, -10);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    polygon.translate(0, 10);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    polygon.translate(-10, 0);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    polygon.translate(10, 0);
                }
                if (e.getKeyCode() == 83) {
                    polygon.scale(1.1f, 1.1f);
                }
                if (e.getKeyCode() == 68) {
                    polygon.scale(0.9f, 0.9f);
                }

                if (e.getKeyCode() == 82) {
                    polygon.rotate(5.0f);
                }
                if (e.getKeyCode() == 84) {
                    polygon.rotate(-5.0f);
                }
                polygon.connectFirstAndLast(img, g);
                g.drawImage(img, 0, 0, (img, infoflags, x, y, width, height) -> false);
            }
        });


        w.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                w.dispose();
                g.dispose();
                System.exit(0);
            }
        });
    }

}
