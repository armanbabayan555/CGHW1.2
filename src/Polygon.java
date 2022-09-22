import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Polygon {
    private ArrayList<Point> points = new ArrayList<>();

    public void addPoint(Point p) {
        points.add(p);
    }

    public void clearPoints() {
        points.clear();
    }

    public void connect(BufferedImage img, Graphics g) {
        int[] pixel = {255, 255, 255};
        for (int i = 0; i < points.size() - 1; i++) {
            Formulas.bresenham((int) points.get(i).getPoint()[0][0],
                    (int) points.get(i).getPoint()[1][0], (int) points.get(i + 1).getPoint()[0][0],
                    (int) points.get(i + 1).getPoint()[1][0], img, g);
        }
    }

    public void connectFirstAndLast(BufferedImage img, Graphics g) {
        int[] pixel = {255, 255, 255};
        connect(img, g);
        Formulas.bresenham((int) points.get(0).getPoint()[0][0], (int) points.get(0).getPoint()[1][0],
                (int) points.get(points.size() - 1).getPoint()[0][0], (int) points.get(points.size() - 1).getPoint()[1][0],
                img, g);
    }

    public Point centroid() {
        int x = 0;
        int y = 0;

        for (Point point : points) {
            x += point.getPoint()[0][0];
            y += point.getPoint()[1][0];
        }

        Point centroid = new Point((x / points.size()), (y / points.size()), 1);
        return centroid;
    }

    public void translate(int tx, int ty) {
        for (Point point : points) {
            point.translate(tx, ty);
        }
    }

    public void rotate(float angle) {
        Point centroid = centroid();

        for (Point p : points) {
            p.rotate((int) centroid.getPoint()[0][0], (int) centroid.getPoint()[1][0], angle);
        }
    }

    public void scale(float sx, float sy) {
        Point centroid = centroid();
        for (Point p : points) {
            p.scale((int) centroid.getPoint()[0][0], (int) centroid.getPoint()[1][0], sx, sy);
        }
    }

}
