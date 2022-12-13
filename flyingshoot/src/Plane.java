import javax.swing.*;
import java.util.Random;

/**
 * 英雄攻擊對象 需繼承實現
 */

public abstract class Plane extends FlyingObjects {
    public Plane() {
    }

    public Plane(double x, double y, ImageIcon image, ImageIcon[] images, ImageIcon[] bom) {
        super(x, y, image, images, bom);
    }

    public Plane(ImageIcon image, ImageIcon[] images, ImageIcon[] bom) {
        Random random = new Random();
        this.image = image;
        this.images = images;
        this.bom = bom;
        width = image.getIconWidth();
        height = image.getIconHeight();

        x = random.nextInt((int) (World.WIDTH - width));
        y = -height;
    }

    public void move() {
        y += step;
    }

}
