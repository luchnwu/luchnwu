package teris;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;

public class Tetris extends JPanel {
    /**
     * 遊戲狀態 RUNNING = 0, PAUSE = 1, GAME_OVER = 2
     */
    private int state;
    public static final int RUNNING = 0;
    public static final int PAUSE = 1;
    public static final int GAME_OVER = 2;
    /**
     * 遊戲屬性 得分
     */
    private int score;
    /**
     * 遊戲屬性 消滅線條數
     */
    private int lines;
    /**
     * 遊戲屬性 遊戲進階
     */
    private int level;
    /**
     * 遊戲時間
     */
    private int index;
    /**
     * 遊戲速度
     */
    private int speed;
    /**
     * 方塊牆
     */
    private Cell[][] wall = new Cell[ROWS][COLS];
    public static final int ROWS = 20;
    public static final int COLS = 10;
    /**
     * 方塊物件
     */
    private Tetromino tetromino;
    /**
     * 等待方塊物件
     */
    private Tetromino nextOne;

    /**
     * 畫背景
     */
    public void paintBackground(Graphics g) {
        Images.BACKGROUND.paintIcon(null, g, 0, 0);
    }

    /**
     * 畫遊戲狀態
     */
    public void paintState(Graphics g) {
        switch (state) {
            case GAME_OVER:
                Images.GAME_OVER.paintIcon(null, g, -14, -14);
                break;
            case PAUSE:
                Images.PAUSE.paintIcon(null, g, -14, -14);
        }
    }

    /**
     * 畫分數 銷線 遊戲進階
     */
    public void paintScore_Lines_Level(Graphics g) {
        //畫面位置
        int x = 305;
        int y = 165;
        //設定顏色及大小
        Color color = new Color(0xffffff);
        g.setColor(color);
        Font font = new Font(Font.SERIF, Font.BOLD, 40);
        g.setFont(font);
        //畫上文字及調整位置
        g.drawString("SCORE:" + score, x, y);
        y += 56;
        g.drawString("LINES:" + lines, x, y);
        y += 56;
        g.drawString("LEVEL:" + level, x, y);

        //增加文字效果
        x = 303;
        y = 163;
        color = new Color(0x667799);
        g.setColor(color);
        g.drawString("SCORE:" + score, x, y);
        y += 56;
        g.drawString("LINES:" + lines, x, y);
        y += 56;
        g.drawString("LEVEL:" + level, x, y);
    }

    /**
     * 方格大小
     */
    public static final int CELL_SIZE = 26;

    /**
     * 方塊遊戲區
     */
    public void paintWall(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                //wall[row][col] =new Cell();測試
                Cell cell = wall[row][col];
                //cell.setImage(Images.I);測試
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                if (cell != null) {
                    cell.paint(g, x, y);
                }
            }
        }
    }

    /**
     * 畫方塊
     */
    public void paintTetromino(Graphics g) {
        if (tetromino == null) {
            return;
        }
        Cell[] cells = tetromino.cells;
        for (Cell cell : cells) {
            int col = cell.getCol();
            int row = cell.getRow();
            int x = col * CELL_SIZE;
            int y = row * CELL_SIZE;
            cell.paint(g, x, y);
        }
    }

    /**
     * 畫方塊準備區
     */
    public void paintNextOne(Graphics g) {
        if (nextOne == null) {
            return;
        }
        Cell[] cells = nextOne.cells;
        for (Cell cell : cells) {
            int col = cell.getCol() + 10;
            int row = cell.getRow() + 1;
            int x = col * CELL_SIZE;
            int y = row * CELL_SIZE;
            cell.paint(g, x, y);
        }
    }

    /**
     * 遊戲畫面
     */
    @Override
    public void paint(Graphics g) {
        paintBackground(g);
        g.translate(14, 14);
        paintScore_Lines_Level(g);
        paintWall(g);
        paintTetromino(g);
        paintNextOne(g);
        paintState(g);
    }

    /**
     * 遊戲規則邏輯 是否超出邊界
     */
    private boolean outOfBounds() {
        Cell[] cells = tetromino.cells;
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if (col < 0 || col >= COLS || row < 0 || row > ROWS) {
                return true;
            }
        }
        return false;
    }

    /**
     * 遊戲規則邏輯 是否和牆上是同一塊
     */
    private boolean concide() {
        Cell[] cells = tetromino.cells;
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if (wall[row][col] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 遊戲規則邏輯 是否可以下降
     */
    private boolean canDrop() {
        Cell[] cells = tetromino.cells;
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if (row == (ROWS - 1)) {
                return false;
            }
            if (wall[row + 1][col] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 遊戲規則邏輯 是否一行
     */
    private boolean isFullRow(int row) {
        Cell[] cells = wall[row];
        for (Cell cell : cells) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 遊戲規則邏輯 是否遊戲結束
     */
    private boolean isGameOver() {
        Cell[] cells = nextOne.cells;
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if (wall[row][col] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 遊戲方法 嵌入方塊牆
     */
    private void landIntoWall() {
        Cell[] cells = tetromino.cells;
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            wall[row][col] = cell;
        }
    }

    /**
     * 遊戲方法 消去行
     */
    private void deleteRow(int row) {
        for (int i = row; i > 0; i--) {
            System.arraycopy(wall[i - 1], 0, wall[i], 0, COLS);
        }
        Arrays.fill(wall[0], null);
    }

    /**
     * 遊戲方法邏輯 消去多行
     *
     * @return 消去行數
     */
    private int destroyLines() {
        int lines = 0;
        for (int row = 0; row < ROWS; row++) {
            if (isFullRow(row)) {
                deleteRow(row);
                lines++;
            }
        }
        return lines;
    }

    /**
     * 遊戲控制邏輯 往右移動
     */
    public void moveRightAction() {
        tetromino.moveRight();
        if (outOfBounds() || concide()) {
            tetromino.moveLeft();
        }
    }

    /**
     * 遊戲控制邏輯 往左移動
     */
    public void moveLeftAction() {
        tetromino.moveLeft();
        if (outOfBounds() || concide()) {
            tetromino.moveRight();
        }
    }

    /**
     * 遊戲控制邏輯 順時針旋轉
     */
    public void rotateRightAction() {
        tetromino.rotateRight();
        if (outOfBounds() || concide()) {
            tetromino.rotateLeft();
        }
    }

    /**
     * 遊戲控制邏輯 逆時針旋轉
     */
    public void rotateLeftAction() {
        tetromino.rotateLeft();
        if (outOfBounds() || concide()) {
            tetromino.rotateRight();
        }
    }

    //依消去行數增加分數           0  1   2   3    4
    private int[] scoreTable = {0, 10, 50, 80, 200};

    /**
     * 遊戲控制邏輯 正常下降
     */
    public void softDropAction() {
        if (canDrop()) {
            tetromino.softDrop();
        } else {
            landIntoWall();
            int lines = destroyLines();
            this.lines += lines;
            score += scoreTable[lines];
            if (isGameOver()) {
                state = GAME_OVER;
            } else {
                tetromino = nextOne;
                nextOne = Tetromino.randomOne();
            }
        }
    }

    /**
     * 鍵盤監聽控制 Running
     */
    protected void processRunningKey(int key) {
        switch (key) {
            case KeyEvent.VK_RIGHT:
                moveRightAction();
                break;
            case KeyEvent.VK_LEFT:
                moveLeftAction();
                break;
            case KeyEvent.VK_DOWN:
                softDropAction();
                break;
            case KeyEvent.VK_UP:
                rotateRightAction();
                break;
            case KeyEvent.VK_Z:
                rotateLeftAction();
                break;
//            case KeyEvent.VK_SPACE:hardDropAction();break;
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_P:
                state = PAUSE;
                break;

        }
    }

    /**
     * 鍵盤監聽控制 Pause
     */
    protected void processPauseKey(int key) {
        switch (key) {
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_C:
                state = RUNNING;
                index = 1;
                break;
        }
    }

    /**
     * 鍵盤監聽控制 Game_over
     */
    protected void processGameOverKey(int key) {
        switch (key) {
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_S:
                wall = new Cell[ROWS][COLS];
                tetromino = Tetromino.randomOne();
                nextOne = Tetromino.randomOne();
                score = 0;
                lines = 0;
                index = 0;
                state = RUNNING;
        }
    }

    /**
     * 時間器
     */
    private Timer timer;

    /**
     * 遊戲開始初始化
     */
    public void action() {
        tetromino = Tetromino.randomOne();
        nextOne = Tetromino.randomOne();
        state = RUNNING;

        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (state) {
                    case RUNNING:
                        processRunningKey(key);
                        break;
                    case PAUSE:
                        processPauseKey(key);
                        break;
                    case GAME_OVER:
                        processGameOverKey(key);
                        break;
                }
                Tetris.this.repaint();
            }
        };
        addKeyListener(listener);
        setFocusable(true);
        requestFocus();

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                speed = 40 - (score / 1000);
                speed = Math.max(speed, 1);
                level = 41 - speed;
                if (index % speed == 0) {
                    if (state == RUNNING) {
                        softDropAction();
                    }
                }
                index++;
                repaint();
            }
        };
        timer.schedule(task, 10, 10);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Tetris tetris = new Tetris();
        frame.add(tetris);
        frame.setSize(525, 550);
        frame.setLocationRelativeTo(null);
        //有無視窗控制
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        tetris.action();
    }
}
