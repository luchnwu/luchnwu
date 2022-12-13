/**
 * 敵機二型
 */
public class BigPlane extends Plane implements Enemy{
    public BigPlane() {
        super(Images.BIG_PLANE[0], Images.BIG_PLANE, Images.BOM);
        step = Math.random() * 3.0 + 0.5;
        life = 3;
    }

    public BigPlane(double x, double y, double step) {
        super(x, y, Images.BIG_PLANE[0], Images.BIG_PLANE, Images.BOM);
        this.step = step;
        life = 3;
    }

    @Override
    public int getScore() {
        return 100;
    }
}
