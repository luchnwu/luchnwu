import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * 飛行物件父類
 * 不可直接使用 需繼承實現
 */
public abstract class FlyingObjects {

    //腳色狀態靜態參數
    public static final int LIVING = 1;
    public static final int DEAD = 0;
    public static final int ZOMBIE = -1;

    //成員屬性
    //初始生命狀態
    protected int state = LIVING;
    protected int life = 1;

    //初始座標位置
    protected double x;
    protected double y;

    //初始方向速度
    protected double step;

    //腳色大小
    protected double width;
    protected double height;

    //腳色圖片與動畫
    protected ImageIcon image;
    protected ImageIcon[] images;

    //爆炸動畫
    protected ImageIcon[] bom;


    //構造
    public FlyingObjects() {
    }

    public FlyingObjects(double x, double y, ImageIcon image, ImageIcon[] images, ImageIcon[] bom) {

        this.x = x;
        this.y = y;
        this.image = image;
        this.images = images;
        this.bom = bom;
        width = image.getIconWidth();
        height = image.getIconHeight();

    }

    //物件可執行動作

    /**
     * 飛行物件需各自實現移動方式
     */
    public abstract void move();


    //index影響動畫切哪一張 i影響爆炸速度
    protected int index = 0;
    protected int i = 0;

    /**
     * 切換動畫圖片
     */
    public void nextImage() {
        switch (state) {
            case LIVING:
                if (images == null) {
                    return;
                }
                image = images[index++ / 30 % images.length];
                break;

            case DEAD:
                int index = i++ / 10;
                if (bom == null) {
                    return;
                }

                if (index == bom.length) {
                    state = ZOMBIE;
                    return;
                }
                image = bom[index];

        }

    }

    /**
     * 用圖片產生背景
     */
    public void paint(Graphics g) {
        image.paintIcon(null, g, (int) x, (int) y);
        nextImage();
    }

    /**
     * 是否被射擊
     */
    public boolean isHIT() {

        if (life > 0) {
            life--;
            //如果生命歸零則死亡
            if (life == DEAD) {
                state = DEAD;
            }
            return true;
        }
        return false;
    }

    /**
     * 是否直接死亡
     */
    public boolean goDead() {

        if (state == LIVING) {
            life = DEAD;
            state = DEAD;

            return true;
        }
        return false;
    }

    /**
     * 使否超出畫面
     */
    public boolean outOfBackground() {
        return y > World.HEIGHT + 50 || y < -height - 50;
    }

    /**
     * 是否活著
     */
    public boolean isLIVING() {
        return state == LIVING;
    }

    /**
     * 是否死亡
     */
    public boolean isDEAD() {
        return state == DEAD;
    }

    /**
     * 是否不動
     */
    public boolean isZOMBIE() {
        return state == ZOMBIE;
    }

    /**
     * 使否碰撞爆炸
     */
    public boolean isBOM(FlyingObjects flyingObject) {
        //飛行物件各取一個內圓 計算兩個圓是否接觸
        double r1 = Math.min(width, height) / 2.0;
        double r2 = Math.min(flyingObject.width, flyingObject.height) / 2.0;
        //飛行物件的圓心點座標
        double x1 = x + width / 2.0;
        double y1 = y + height / 2.0;

        double x2 = flyingObject.x + flyingObject.width / 2.0;
        double y2 = flyingObject.y + flyingObject.height / 2.0;
        //計算兩個座標的距離
        double distance_x = x2 - x1;
        double distance_y = y2 - y1;
        double distance = Math.sqrt(distance_x * distance_x + distance_y * distance_y);

        return distance < r1 + r2;
    }

    @Override
    public String toString() {
        return "FlyingObjects{" +
                "state=" + state +
                ", life=" + life +
                ", x=" + x +
                ", y=" + y +
                ", step=" + step +
                ", width=" + width +
                ", height=" + height +
                ", image=" + image +
                ", images=" + Arrays.toString(images) +
                ", bom=" + Arrays.toString(bom) +
                ", index=" + index +
                ", i=" + i +
                '}';
    }
}
