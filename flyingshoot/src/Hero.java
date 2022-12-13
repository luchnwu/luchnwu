import java.awt.*;

/**
 * 主角英雄
 */
public class Hero extends FlyingObjects {
    public Hero() {
        super(138, 380, Images.HERO[0], Images.HERO, Images.BOM);
    }

    public Hero(double x, double y) {
        super(x, y, Images.HERO[0], Images.HERO, Images.BOM);
    }

    @Override
    public void move() {
    }

    /**
     * 英雄跟著滑鼠鼠標移動
     */
    public void move(double x, double y) {
        this.x = x - width / 2.0;
        this.y = y - height / 2.0;
    }

    /**
     * 英雄發射子彈
     */
    public Bullet fire() {
        double x = this.x + width / 2.0 - 5.0;
        double y = this.y - 5.0;

        return new Bullet(x, y);
    }

    //擁有雙火的時間
    private int doubleFire = 0;

    public void getDoubleFire() {
        doubleFire += 20;
    }

    public Bullet[] openFire() {
        if (doubleFire > 0) {
            doubleFire--;
            double x = this.x + width / 2.0 - 5.0;
            double y = this.y - 5.0;
            Bullet bullet1 = new Bullet(x + 15.0, y);
            Bullet bullet2 = new Bullet(x - 15.0, y);
            return new Bullet[]{bullet1, bullet2};
        } else {
            Bullet bullet = fire();
            return new Bullet[]{bullet};
        }
    }

}
