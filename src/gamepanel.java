import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
//в классе gamepanel происходит формирование общего изображения
//где мы формируем в виртуальном окне общую картинку всех элементов.

public class gamepanel extends JPanel implements Runnable
{
    //Field
    // задаём размер панели
    public static  int Width = 1024;//static для доступа в другие ;классы к статическому полю можно обращаться не создавая экземпляр класса
    public static  int Height = 600;

    public static int mouseX;// координаты мышки
    public static int mouseY;// координаты мышки
    public static boolean leftMouse;
    pravmenue button4 = new pravmenue(800,20,140,40,"C:/Users/user/IdeaProjects/Game1/res/but3.png","Назад");
    public static boolean buttmenue = true; //главная страница меню
    public static boolean pravmenue = false; // страница меню правил

    private Thread thread;// Создаем поток- ссылка на обьект класса Thread

    private BufferedImage image;// ссылка на обьект класса
    private Graphics2D g;

    private int FPS;//
    private double millisToFps;// fps в миллсек
    private long timerFPS;// таймер fps
    private int sleepTime; //сколько он будет спать

    public static   enum STATES{MENUE,PLAY} //обьявляем перечсления
    public static STATES state = STATES.MENUE;// переменная меню

    public static GameBack background;// ссылка на обьект класса
    public static Player player;// ссылка на обьект класса
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Wave wave;// ссылка на обьект класса
    public static Menue menue;// ссылка на обьект класса
    public static Aim aim1;// ссылка на обьект класса
    public static Audio a_bul;//звук выстрела
    public static Audio a_enem;//звук смерти врага
    public static Audio a_reload1;//перезарядка
    public static Audio a_wave;//звук волны
    public static Audio a_shagi;//звук шагов

    //constructor
    public gamepanel()//выход конструктора Jpanel
    {
        super();// активируем консруктор родителя

        setPreferredSize(new Dimension(Width, Height));// размер передаем в обьект класса Измерения
        setFocusable(true);//передаем фокус
        requestFocus();// акивируем
        addKeyListener(new Listeners());// добавляем обработчик событий клик мышь
        addMouseMotionListener(new Listeners());// добавляем обработчик событий клаава
        addMouseListener(new Listeners());//добавляем обработчик событий перем мышь

    }
    //Functions
    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();// запускаем поток
    }
    //метод от интерфейса Runnable (потока)
    public void run()
    {
        FPS = 30;// задаем желаемый FPS
        millisToFps = 1000/FPS;//пересчет в миллисек
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
            if(state.equals(STATES.MENUE))// если пер state == MENUE
            {this.setCursor(mainCursor);//активировать игровой курсор
                if (buttmenue)
                {
                    background.draw(g);// рисуем фон
                    menue.draw(g);// рисуем меню
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
            if(state.equals(STATES.PLAY))// игра
            {
                gameUpdate(); //обновление
                gameRender(); //перерисовка
                gameDraw(); ////перенос изображения на панель
            }



            timerFPS = (System.nanoTime() - timerFPS)/1000000;//сколько прошло миллсек на операции выше
            if(millisToFps > timerFPS)   //чтоб не уйти в минус
            {
                sleepTime = (int)(millisToFps - timerFPS);
            } else sleepTime = 1;

            try {
                Thread.sleep(sleepTime); // независимо от цикла полуячаем 30 фпс

            } catch (InterruptedException e)//если не удается заснуть- исключение
            {
                e.printStackTrace();
            }
            timerFPS = 0;// обнуляем таймер
            sleepTime = 1;// обновляем время сна
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
                        gamepanel.state = STATES.MENUE; //переход в меню
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
            bullets.get(i).update();// обновлям текущую пулю
            boolean remove = bullets.get(i).remove();//текущую пулю проверяем где она
            if(remove) // если правдиво(улетела)
            {
              bullets.remove(i);//удаляем пулю которая вылетела
              i--;
            }
        }
        //enemies Update
        for(int i = 0; i < enemies.size(); i ++)
        {
            enemies.get(i).update();// обновлям текущего врага
        }

        //bullets-enemies Collide
        for(int i = 0;i < enemies.size();i++)// каждого врвга из списка
        {
            Enemy e = enemies.get(i);// выделяем элемент списка
            double ex = e.getX();// получаем коорд элемента
            double ey = e.getY();
            double eh = e.getH();
            double ew = e.getW();
            for(int j = 0;j < bullets.size();j++)
            {
                Bullet b = bullets.get(j);// выделяем элемент списка
                double bx = b.getX();// получаем коорд элемента
                double by = b.getY();
                double bw = b.getW();
                double bh = b.getH();
              //  double dist = Math.sqrt(dx * dx + dy * dy);//расстояние друг от друга//
                if((bx > ex - bw) && (bx < ex + ew) && (by > ey - bh) && (by < ey + eh))//проверка
                {
                    e.hit();// метод уменьшения здоровья врага
                    bullets.remove(j); // удаляем пулю из списка
                    j--;                            //проверка на столкновение с пулями
                    //Проверка здоровья врага
                    boolean remove_p = e.remove_f();// пер присваив значение метода пров здоров врага
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
            double ex = e.getX();// получаем коорд элемента
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


    public void gameRender()//перерисовка
    {
        //Background draw
        background.draw(g);

        //player draw
        player.draw(g);

        //отображение игрового курсора
        aim1.draw(g);


        //Bullets draw
        for(int i = 0; i < bullets.size(); i++)
        { //перерисовка - вызов метода для bullet
            bullets.get(i).draw(g);
        }
        //enemy draw
        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).draw(g);// рисуем текущего врага
        }

        //Wave draw
        if(wave.showWave())
        {
            wave.draw(g);// вызов метода перерисовки для волны
        }


    }
    //перенос изображения на панель
    private void gameDraw()
    {
        Graphics g2 = this.getGraphics();// переоппред Graphics2d на Graphics
        g2.drawImage(image,0,0,null);//все что попало в буффер выводится на экран
        g2.dispose();//очищаем буффер

    }
}
