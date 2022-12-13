/**
 * 敵機一型
 */
public class Airplane extends Plane implements Enemy{
    public Airplane() {
        super(Images.AIRPLANE[0], Images.AIRPLANE, Images.BOM);
        this.step = Math.random() * 4.0 + 1.5;
    }

    public Airplane(double x, double y, double step) {
        super(x, y, Images.AIRPLANE[0], Images.AIRPLANE, Images.BOM);
        this.step = step;
    }

    @Override
    public int getScore() {
        return 10;
    }
}
