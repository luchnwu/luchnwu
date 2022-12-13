
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class World extends JPanel {
    //遊戲畫面大小
    public static final int WIDTH = 400;
    public static final int HEIGHT = 700;

    //遊戲狀態靜態參數
    public static final int READY = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;

    //遊戲狀態及物件
    private int state = READY;
    private Sky sky;
    private Hero hero;
    private Bullet[] bullets;
    private FlyingObjects[] planes;

    //遊戲時間
    private int index = 0;
    //遊戲次數
    private int life;
    //遊戲分數
    private int score;

    /**
     * 建構世界同時初始化
     */
    public World() {
        init();
    }

    private void init() {
        life = 3;
        score = 0;
        sky = new Sky();
        hero = new Hero();
        bullets = new Bullet[0];
        planes = new FlyingObjects[0];
    }

    /**
     * 遊戲世界匯出
     */
    @Override
    public void paint(Graphics g) {
        sky.paint(g);
        hero.paint(g);

        int i;
        for (i = 0; i < bullets.length; i++) {
            bullets[i].paint(g);
        }

        for (i = 0; i < planes.length; i++) {
            planes[i].paint(g);
        }

        if (state == RUNNING) {
            g.setColor(Color.white);
            g.drawString("SCORE:" + score, 20, 40);
            g.drawString("LIFE:" + life, 20, 60);
        }

        if (state == READY) {
            Images.START.paintIcon(null, g, 0, 0);
        }

        if (state == PAUSE) {
            Images.PAUSE.paintIcon(null, g, 0, 0);
        }

        if (state == GAME_OVER) {
            Images.GAME_OVER.paintIcon(null, g, 0, 0);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        world.action();
    }

    /**
     * 隨機產生各種敵機
     */
    void createPlane() {
        if (index % 16 == 0) {
            Random random = new Random();
            int n = random.nextInt(10);
            FlyingObjects plane;
            switch (n) {
                case 6:
                case 7:
                case 8:
                    plane = new Airplane();
                    break;
                case 9:
                    plane = new Bee();
                    break;
                default:
                    plane = new BigPlane();
            }
            planes = Arrays.copyOf(planes, planes.length + 1);
            planes[planes.length - 1] = plane;
        }
    }

    /**
     * 產生子彈
     */
    void fire() {
        if (hero.isLIVING()) {
            if (index % 15 == 0) {
                Bullet[] bullet = hero.openFire();
                bullets = Arrays.copyOf(bullets, bullets.length + bullet.length);
                System.arraycopy(bullet, 0, bullets, bullets.length - bullet.length, bullet.length);
            }
        }
    }

    /**
     * 子彈命中
     */
    void hitDetection() {
        for (Bullet bullet : bullets) {
            if (bullet.isLIVING()) {
                for (FlyingObjects plane : planes) {
                    if (plane.isLIVING() && plane.isBOM(bullet)) {
                        bullet.goDead();
                        plane.isHIT();
                        getReward(plane);
                    }
                }
            }
        }
    }

    /**
     * 敵機得分或效果
     */
    void getReward(FlyingObjects plane) {
        if (plane.isDEAD()) {
            if (plane instanceof Enemy) {
                score += ((Enemy) plane).getScore();
            }
            if (plane instanceof Award) {
                int type = ((Award) plane).getAward();
                if (type == Award.DOUBLE_FIRE) {
                    hero.getDoubleFire();
                } else if (type == Award.LIFE) {
                    life++;
                }
            }
        }
    }

    /**
     * 所有存活子彈和敵機的移動
     */
    void flyingObjects_living_move() {
        for (Bullet bullet : bullets) {
            if (bullet.isLIVING()) {
                bullet.move();
            }
        }
        for (FlyingObjects plane : planes) {
            if (plane.isLIVING()) {
                plane.move();
            }
        }
    }

    /**
     * 清除殭屍狀態或超出螢幕外的子彈和敵機
     */
    void cleanFlyingObjects() {
        Bullet[] Living_Bullets = new Bullet[bullets.length];
        int index = 0;
        for (Bullet bullet : bullets) {
            if (!bullet.isDEAD() && !bullet.outOfBackground()) {
                Living_Bullets[index++] = bullet;
            }
        }
        bullets = Arrays.copyOf(Living_Bullets, index);

        FlyingObjects[] Living_FlyingObjects = new FlyingObjects[planes.length];
        index = 0;
        for (FlyingObjects plane : planes) {
            if (!plane.isZOMBIE() && !plane.outOfBackground()) {
                Living_FlyingObjects[index++] = plane;
            }
        }
        planes = Arrays.copyOf(Living_FlyingObjects, index);
    }

    /**
     * 檢測英雄是否和敵機發生碰撞
     */
    private void runAway() {
        if (hero.isLIVING()) {
            for (FlyingObjects plane : planes) {
                if (plane.isLIVING() && hero.isBOM(plane)) {
                    hero.goDead();
                    plane.goDead();
                    break;
                }
            }
        } else if (hero.isZOMBIE()) {
            if (life > 0) {
                hero = new Hero();
                for (FlyingObjects plane : planes) {
                    plane.goDead();
                }
                life--;
            } else {
                state = GAME_OVER;
            }
        }
    }

    public void action() {
        Timer timer = new Timer();
        LoopTask task = new LoopTask();
        timer.schedule(task, 100, 1000 / 100);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (state == READY) {
                    state = RUNNING;
                } else if (state == GAME_OVER) {
                    init();
                    state = READY;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (state == RUNNING) {
                    state = PAUSE;
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (state == PAUSE) {
                    state = RUNNING;
                }

            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING) {
                    int x = e.getX();
                    int y = e.getY();
                    hero.move(x, y);
                }
            }
        });
    }

    private class LoopTask extends TimerTask {

        public void run() {
            index++;
            if (state == RUNNING) {
                createPlane();
                fire();
                hitDetection();
                runAway();
                flyingObjects_living_move();
                cleanFlyingObjects();
            }
            sky.move();
            repaint();
        }
    }

}
