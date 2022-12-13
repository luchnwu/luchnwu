package teris;

import java.util.Arrays;
import java.util.Random;

/**
 * 方塊體父類 繼承實現
 */
public abstract class Tetromino extends Cell {
    /**
     * 7種結構 皆為四個方塊組成
     */
    protected Cell[] cells = new Cell[4];
    /**
     * 方塊旋轉狀態 0 2 4
     */
    protected State[] states;

    /**
     * 方塊旋轉狀態 內部類
     */
    protected class State {
        /**
         * 對應四個方塊的畫面座標
         */
        int row0, col0, row1, col1, row2, col2, row3, col3;

        /**
         * 變換座標體位移
         */
        public State(int row0, int col0, int row1, int col1, int row2, int col2, int row3, int col3) {
            this.row0 = row0;
            this.col0 = col0;
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
            this.row3 = row3;
            this.col3 = col3;
        }
    }

    @Override
    public String toString() {
        return "Tetromino  " + this.getClass().getName() + "{" +
                "cells=" + Arrays.toString(cells) +
                '}';
    }

    /**
     * 方塊體下降
     */
    @Override
    public void softDrop() {
        for (Cell cell : cells) {
            cell.softDrop();
        }
    }

    /**
     * 方塊體右移
     */
    @Override
    public void moveRight() {
        for (Cell cell : cells) {
            cell.moveRight();
        }
    }

    /**
     * 方塊體左移
     */
    @Override
    public void moveLeft() {
        for (Cell cell : cells) {
            cell.moveLeft();
        }
    }

    /**
     * 方塊旋轉基礎值
     */
    protected int index = 10000;

    /**
     * 方塊旋轉
     */
    private void rotate() {
        //選定一種狀態 S0->S1->S2->S3->S0->S1
        State state = states[index % states.length];
        //選中方塊原點並取得座標
        Cell cell = cells[0];
        int col = cell.getCol();
        int row = cell.getRow();
        //依原點座標加上"旋轉狀態"位移變換其餘方塊位置
        cells[1].setCol(col + state.col1);
        cells[1].setRow(row + state.row1);
        cells[2].setCol(col + state.col2);
        cells[2].setRow(row + state.row2);
        cells[3].setCol(col + state.col3);
        cells[3].setRow(row + state.row3);
    }

    /**
     * 方塊體順旋轉
     */
    public void rotateRight() {
        index++;
        rotate();
    }

    /**
     * 方塊體逆旋轉
     */
    public void rotateLeft() {
        index--;
        rotate();
    }

    /**
     * 靜態方法隨機產生方塊
     */
    public static Tetromino randomOne() {
        Random random = new Random();
        int type = random.nextInt(7);
        switch (type) {
            case 0:
                return new T();
            case 1:
                return new I();
            case 2:
                return new S();
            case 3:
                return new Z();
            case 4:
                return new O();
            case 5:
                return new L();
            case 6:
                return new J();
        }
        return null;
    }
}
//類繼承區 T I S Z O L J

/**
 * T 構造體實現
 */
class T extends Tetromino {
    public T() {
        cells[0] = new Cell(0, 4, Images.T);
        cells[1] = new Cell(0, 5, Images.T);
        cells[2] = new Cell(1, 4, Images.T);
        cells[3] = new Cell(0, 3, Images.T);
        states = new State[4];
        states[0] = new State(0, 0, 0, 1, 1, 0, 0, -1);
        states[1] = new State(0, 0, 1, 0, 0, -1, -1, 0);
        states[2] = new State(0, 0, 0, -1, -1, 0, 0, 1);
        states[3] = new State(0, 0, -1, 0, 0, 1, 1, 0);
    }
}

/**
 * I 構造體實現
 */
class I extends Tetromino {
    public I() {
        cells[0] = new Cell(0, 4, Images.I);
        cells[1] = new Cell(0, 5, Images.I);
        cells[2] = new Cell(0, 6, Images.I);
        cells[3] = new Cell(0, 3, Images.I);
        states = new State[2];
        states[0] = new State(0, 0, 0, 1, 0, 2, 0, -1);
        states[1] = new State(0, 0, 1, 0, 2, 0, -1, 0);
    }
}

/**
 * S 構造體實現
 */
class S extends Tetromino {
    public S() {
        cells[0] = new Cell(0, 4, Images.S);
        cells[1] = new Cell(1, 5, Images.S);
        cells[2] = new Cell(1, 4, Images.S);
        cells[3] = new Cell(0, 3, Images.S);
        states = new State[2];
        states[0] = new State(0, 0, 1, 1, 1, 0, 0, -1);
        states[1] = new State(0, 0, 1, -1, 0, -1, -1, 0);
    }
}

/**
 * Z 構造體實現
 */
class Z extends Tetromino {
    public Z() {
        cells[0] = new Cell(0, 4, Images.Z);
        cells[1] = new Cell(0, 5, Images.Z);
        cells[2] = new Cell(1, 4, Images.Z);
        cells[3] = new Cell(1, 3, Images.Z);
        states = new State[2];
        states[0] = new State(0, 0, 0, 1, 1, 0, 1, -1);
        states[1] = new State(0, 0, 1, 0, 0, -1, -1, -1);
    }
}

/**
 * O 構造體實現
 */
class O extends Tetromino {
    public O() {
        cells[0] = new Cell(0, 4, Images.O);
        cells[1] = new Cell(0, 5, Images.O);
        cells[2] = new Cell(1, 5, Images.O);
        cells[3] = new Cell(1, 4, Images.O);
        states = new State[2];
        states[0] = new State(0, 0, 0, 1, 1, 1, 1, 0);
        states[1] = new State(0, 0, 0, 1, 1, 1, 1, 0);
    }
}

/**
 * L 構造體實現
 */
class L extends Tetromino {
    public L() {
        cells[0] = new Cell(0, 4, Images.L);
        cells[1] = new Cell(0, 5, Images.L);
        cells[2] = new Cell(1, 5, Images.L);
        cells[3] = new Cell(0, 3, Images.L);
        states = new State[4];
        states[0] = new State(0, 0, 0, 1, 1, 1, 0, -1);
        states[1] = new State(0, 0, 1, 0, 1, -1, -1, 0);
        states[2] = new State(0, 0, 0, -1, -1, -1, 0, 1);
        states[3] = new State(0, 0, -1, 0, -1, 1, 1, 0);
    }
}

/**
 * J 構造體實現
 */
class J extends Tetromino {
    public J() {
        cells[0] = new Cell(0, 4, Images.J);
        cells[1] = new Cell(0, 5, Images.J);
        cells[2] = new Cell(1, 3, Images.J);
        cells[3] = new Cell(0, 3, Images.J);
        states = new State[4];
        states[0] = new State(0, 0, 0, 1, 1, -1, 0, -1);
        states[1] = new State(0, 0, 1, 0, -1, -1, -1, 0);
        states[2] = new State(0, 0, 0, -1, -1, 1, 0, 1);
        states[3] = new State(0, 0, -1, 0, 1, 1, 1, 0);
    }
}