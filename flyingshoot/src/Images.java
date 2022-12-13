import javax.swing.*;

/**
 * 靜態加載圖片資源
 */
public class Images {

    public static ImageIcon[] AIRPLANE = new ImageIcon[2];
    public static ImageIcon[] BIG_PLANE = new ImageIcon[2];
    public static ImageIcon[] BEE = new ImageIcon[2];
    public static ImageIcon[] BOM = new ImageIcon[4];
    public static ImageIcon[] HERO = new ImageIcon[2];
    public static ImageIcon BULLET;
    public static ImageIcon BACKGROUND;
    public static ImageIcon START;
    public static ImageIcon PAUSE;
    public static ImageIcon GAME_OVER;

    static {
        AIRPLANE[0] = new ImageIcon("images/airplane0.png");
        AIRPLANE[1] = new ImageIcon("images/airplane1.png");
        BIG_PLANE[0] = new ImageIcon("images/bigairplane0.png");
        BIG_PLANE[1] = new ImageIcon("images/bigairplane1.png");
        BEE[0] = new ImageIcon("images/bee0.png");
        BEE[1] = new ImageIcon("images/bee1.png");
        BOM[0] = new ImageIcon("images/bom4.png");
        BOM[1] = new ImageIcon("images/bom3.png");
        BOM[2] = new ImageIcon("images/bom2.png");
        BOM[3] = new ImageIcon("images/bom1.png");
        HERO[0] = new ImageIcon("images/hero0.png");
        HERO[1] = new ImageIcon("images/hero1.png");
        BULLET = new ImageIcon("images/bullet.png");
        BACKGROUND = new ImageIcon("images/background.png");
        START = new ImageIcon("images/start.png");
        PAUSE = new ImageIcon("images/pause.png");
        GAME_OVER = new ImageIcon("images/gameover.png");
    }

}
