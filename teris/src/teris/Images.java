package teris;

import javax.swing.*;


/**
 * 靜態加載圖片資源
 */
public class Images {
    /**
     * 背景圖片
     */
    public static ImageIcon BACKGROUND;
    /**
     * 背景暫停圖片
     */
    public static ImageIcon PAUSE;
    /**
     * 背景結束遊戲圖片
     */
    public static ImageIcon GAME_OVER;
    /**
     * 方塊 T 圖片
     */
    public static ImageIcon T;
    /**
     * 方塊 S 圖片
     */
    public static ImageIcon S;
    /**
     * 方塊 Z 圖片
     */
    public static ImageIcon Z;
    /**
     * 方塊 L 圖片
     */
    public static ImageIcon L;
    /**
     * 方塊 J 圖片
     */
    public static ImageIcon J;
    /**
     * 方塊 O 圖片
     */
    public static ImageIcon O;
    /**
     * 方塊 I 圖片
     */
    public static ImageIcon I;

    static {
        BACKGROUND = new ImageIcon("images/tetris.png");
        GAME_OVER = new ImageIcon("images/game-over.png");
        PAUSE = new ImageIcon("images/pause.png");
        T = new ImageIcon("images/T.png");
        S = new ImageIcon("images/S.png");
        Z = new ImageIcon("images/Z.png");
        J = new ImageIcon("images/J.png");
        L = new ImageIcon("images/L.png");
        O = new ImageIcon("images/O.png");
        I = new ImageIcon("images/I.png");
    }
}
