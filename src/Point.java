
public class Point {

    public float[][] getPoint() {
        return point;
    }

    private float[][] point = new float[3][1];

    public Point(int x, int y, int w) {
        this.point[0][0] = x;
        this.point[1][0] = y;
        this.point[2][0] = w;
    }

    public void translate(int tx, int ty) {
        float[][] trans = Formulas.getTranslationMatrix(tx, ty);
        point = Formulas.matrixMultiplication(trans, point);

    }

    public void rotate(int cx, int cy, float angle) {
        point = Formulas.matrixMultiplication(Formulas.getRotationMatrix(cx, cy, angle), point);

    }

    public void scale(int cx, int cy, float sx, float sy) {
        point = Formulas.matrixMultiplication(Formulas.getScalingMatrix(cx, cy, sx, sy), point);
    }
}
