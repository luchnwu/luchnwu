/**
 * 特殊敵機
 */
public class Bee extends Plane implements Enemy, Award {
    //改變左右方向
    protected int direction;

    public Bee() {
        super(Images.BEE[0], Images.BEE, Images.BOM);
        step = Math.random() * 5.0 + 2.0;
        direction = Math.random() > 0.5 ? 1 : -1;
    }

    public Bee(double x, double y, double step) {
        super(x, y, Images.BEE[0], Images.BEE, Images.BOM);
        this.step = step;
        direction = Math.random() > 0.5 ? 1 : -1;
    }

    @Override
    public void move() {
        super.move();
        x += direction;

        if (x < 0 || x > World.WIDTH - width) {
            direction = -direction;
        }

    }

    @Override
    public int getScore() {
        return 50;
    }

    /**
     * @return 1.DOUBLE_FIRE 2.LIFE
     */
    @Override
    public int getAward() {
        return Math.random() > 0.5 ? 1 : 2;
    }
}
