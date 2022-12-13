package teris;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 俄羅斯方塊遊戲基本類型 方塊
 */
public class Cell {
    /**
     * 方塊在畫面中的位置 行 X
     */
    private int col;
    /**
     * 方塊在畫面中的位置 列 Y
     */
    private int row;
    /**
     * 方塊圖案顏色
     */
    private ImageIcon image;

    /**
     * 無參數構造體
     */
    public Cell() {
    }

    /**
     * 全參構造體
     */
    public Cell(int row, int col, ImageIcon image) {
        this.row = row;
        this.col = col;
        this.image = image;
    }

    //基本get set 方法區
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "col=" + col +
                ", row=" + row +
                '}';
    }

    /**
     * 方塊著色
     */
    public void paint(Graphics g, int x, int y) {
        getImage().paintIcon(null, g, x, y);
    }

    /**
     * 方塊下降
     */
    public void softDrop() {
        row++;
    }

    /**
     * 方塊右移
     */
    public void moveRight() {
        col++;
    }

    /**
     * 方塊左移
     */
    public void moveLeft() {
        col--;
    }
}
