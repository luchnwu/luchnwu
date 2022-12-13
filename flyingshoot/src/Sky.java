import java.awt.*;

public class Sky extends FlyingObjects {

    //同時產生兩張圖讓圖片在背景循環
    private double y0;

    public Sky() {
        super(0.0, 0.0, Images.BACKGROUND, null, null);
        step = 0.8;
        y0 = -height;
    }

    @Override
    public void move() {
        y += step;
        y0 += step;

        //跑出外面就跳回第二張
        if (y > height) {
            y = -height;
        }
        if (y0 > height) {
            y0 = -height;
        }
    }

    @Override
    public void paint(Graphics g) {
        image.paintIcon(null, g, (int) x, (int) y);
        image.paintIcon(null, g, (int) x, (int) y0);
    }
}
