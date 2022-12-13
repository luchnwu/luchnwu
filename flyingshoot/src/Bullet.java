import javax.swing.*;

public class Bullet extends FlyingObjects {

    public Bullet(double x, double y) {
        super(x, y, Images.BULLET, null, null);
        step = 4.0;
    }

    @Override
    public void move() {
        y -= step;
    }
}
