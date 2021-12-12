import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class gamepanel extends JPanel implements Runnable
{
    //Field
    public static  int Width = 1024;//static для доступа в другие классы
    public static  int Height = 600;

    public static int mouseX;
    public static int mouseY;
    public static boolean leftMouse;
    pravmenue button4 = new pravmenue(800,20,140,40,"C:/Users/user/IdeaProjects/Game1/res/but3.png","Назад");
    public static boolean buttmenue = true; //главная страница меню
    public static boolean pravmenue = false; // страница меню правил

    private Thread thread;

    private BufferedImage image;
    private Graphics2D g;

    private int FPS;
    private double millisToFps;
    private long timerFPS;
    private int sleepTime;

    public static enum STATES
    {
        MENUE,
        PLAY
    }
    public static STATES state = STATES.MENUE;

    public static GameBack background;
    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Wave wave;
    public static Menue menue;
    public static Aim aim1;
    public static Audio a_bul;//звук выстрела
    public static Audio a_enem;//звук смерти врага
    public static Audio a_reload1;//перезарядка
    public static Audio a_wave;//звук волны
    public static Audio a_shagi;//звук шагов

    //constructor
    public gamepanel()//выход конструктора Jpanel
    {
        super();

        setPreferredSize(new Dimension(Width, Height));
        setFocusable(true);
        requestFocus();
        addKeyListener(new Listeners());
        addMouseMotionListener(new Listeners());
        addMouseListener(new Listeners());

    }
    //Functions
    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }
    public void run()
    {
        FPS = 30;
        millisToFps = 1000/FPS;
        sleepTime = 0;

        image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();//приведём тип данных image.getGraphics к типу данных(Graphics2D)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//сглаживание между крайними пикселями


        leftMouse = false;
        background = new GameBack();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        wave = new Wave();
        menue = new Menue();
        aim1 = new Aim(gamepanel.mouseX,gamepanel.mouseY,33,33,"C:/Users/user/IdeaProjects/Game1/res/aim1.png",0,0);


        Toolkit kit = Toolkit.getDefaultToolkit();

        Cursor mainCursor = kit.getDefaultToolkit().createCustomCursor(kit.getDefaultToolkit().getImage(""),new Point(0,0),"myCursor");
        //new Point() - это положение "горячей точки" в координатах курсора, точки которая реагирует на действия.

        a_bul = new Audio("C:/Users/user/IdeaProjects/Game1/res/export.wav",0.68);
        a_enem = new Audio("C:/Users/user/IdeaProjects/Game1/res/zd.wav",0.68);
        a_reload1 = new Audio("C:/Users/user/IdeaProjects/Game1/res/akreload.wav",0.68);
        a_wave = new Audio("C:/Users/user/IdeaProjects/Game1/res/wave.wav",0.48);
        a_shagi = new Audio("C:/Users/user/IdeaProjects/Game1/res/shagi.wav",0.58,300);

        while (true)// игровой цикл
        {
            timerFPS = System.nanoTime();
            aim1.update();
            if(state.equals(STATES.MENUE))
            {this.setCursor(mainCursor);//активировать игровой курсор
                if (buttmenue)
                {
                    background.draw(g);
                    menue.draw(g);
                    menue.moveButt(menue.button1);
                    menue.moveButt(menue.button2);
                    menue.moveButt(menue.button3);
                    aim1.draw(g);

                }
                if (pravmenue)
                {
                    background.draw(g);
                    moveSettButt();
                    aim1.draw(g);
                }
                background.update();
                gameDraw();
            }
            if(state.equals(STATES.PLAY))
            {
                gameUpdate();//с каждым проходом цикла обновление картинки
                gameRender();
                gameDraw();
            }



            timerFPS = (System.nanoTime() - timerFPS)/1000000;
            if(millisToFps > timerFPS)   //чтоб не уйти в минус
            {
                sleepTime = (int)(millisToFps - timerFPS);
            } else sleepTime = 1;

            try {
                Thread.sleep(sleepTime); // независимо от цикла полуячаем 30 фпс

            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            timerFPS = 0;
            sleepTime = 1;
        }


    }

    private void moveSettButt()
    {
        {   button4.draw(g);
            if (gamepanel.mouseX > button4.getX() && gamepanel.mouseX < button4.getX() + button4.getW() &&
                    gamepanel.mouseY > button4.getY() && gamepanel.mouseY < button4.getY() + button4.getH()){
                button4.s = "C:/Users/user/IdeaProjects/Game1/res/but4.png";
                if(button4.equals(button4))
                {button4.f="назад";
                    if (menue.click)
                    {  //клик ЛКМ
                        gamepanel.pravmenue = false;
                        gamepanel.buttmenue = true;
                        gamepanel.state = STATES.MENUE; //переход в игру
                    }
                }
            } else{
                button4.s = "C:/Users/user/IdeaProjects/Game1/res/but3.png";
                if(button4.equals(button4)) {button4.f="НАЗАД";}
            }
        }
    }

    public void gameUpdate()
    {
        //Background update
        background.update();
        //Player update
        player.update();


        if (player.health<=0)
        {
            JOptionPane.showMessageDialog(null, "GAME OVER");
            System.exit(1);//выход в систему;//удаление элемента
        }
        //Bullets update
        for(int i=0;i < bullets.size(); i++)
        {
            bullets.get(i).update();
            boolean remove = bullets.get(i).remove();
            if(remove)
            {
              bullets.remove(i);
              i--;
            }
        }
        //enemies Update
        for(int i = 0; i < enemies.size(); i ++)
        {
            enemies.get(i).update();

        }

        //bullets-enemies Collide
        for(int i = 0;i < enemies.size();i++)
        {
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            double eh = e.getH();
            double ew = e.getW();
            for(int j = 0;j < bullets.size();j++)
            {
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();
                double bw = b.getW();
                double bh = b.getH();
              //  double dist = Math.sqrt(dx * dx + dy * dy);//расстояние друг от друга//
                if((bx > ex - bw) && (bx < ex + ew) && (by > ey - bh) && (by < ey + eh))//проверка
                {
                    e.hit();
                    bullets.remove(j);
                    j--;                            //проверка на столкновение с пулями
                    boolean remove_p = e.remove_f();
                    if (remove_p) // если враг повержен
                    {
                        enemies.remove(i);//удаление
                        a_enem.sound();//звук уничтожения
                        a_enem.setVolume();
                        i--;
                        break;
                    }
                }
            }
            }


        //Player-enemy collides
        for(int i = 0; i < enemies.size(); i++)//каждого врага из списка
        {
            Enemy e = enemies.get(i);//выделяем элемент
            double ex = e.getX();
            double ey = e.getY();
            double ew = e.getW();
            double eh = e.getH();

            double px = player.getX();//получаем коорд. элемента
            double py = player.getY();
            double pw = player.getW();
            double ph = player.getH();
            if((px > ex - pw) && (px < ex + ew) && (py > ey - ph) && (py < ey + eh))
            {
                e.hit();
                player.hit();
                //проверка здоровья enemy
                boolean remove_p = e.remove_f(); //пер.присваеваем значение метода пров хп врага
                if (remove_p)//если true то удалить элемент из списка врагов
                {
                    enemies.remove(i);
                    i--;
                }
            }
        }

        //Wave update
        wave.update();
    }


    public void gameRender()
    {
        //Background draw
        background.draw(g);

        //player draw
        player.draw(g);

        //отображение игрового курсора
        aim1.draw(g);


        //Bullets draw
        for(int i = 0; i < bullets.size(); i++)
        {
            bullets.get(i).draw(g);
        }
        //enemy draw
        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).draw(g);
        }

        //Wave draw
        if(wave.showWave())
        {
            wave.draw(g);
        }


    }
    private void gameDraw()
    {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();//очищаем буффер

    }
}
