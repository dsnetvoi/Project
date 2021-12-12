import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.TimerTask;

public class Player {
    //fields
    private  double x;//координаты player
    private double y;//
    private double w;//ширина player
    private double h;//высота player


    private double dx;//Коэф смещения x
    private double dy;//Коэф смещения y

    private int speed;

    public double health;

    private Color color1;
    private Color color2;

    private double angl;//угол поворота
    private double distX;//разница по x от курсора
    private double distY;//
    private double dist;//расстояие от курсора

    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean isFiring;
    public static int magazine;

    ArrayList <String> g_list = new ArrayList<>();//кол картинок для анимации движ
    private String s;//строка адреса для анимирования


    public Rectangle getRect()
    {
        return new Rectangle((int) x,(int) y,41,61);//возврат конструктор с размером объекта
    }


    //Constructor
    public Player()
    {
        x = gamepanel.Width / 2 -30;
        y = gamepanel.Height / 2 -30;
        w = 41;
        h = 61;

        speed = 7;
        dx = 0;
        dy = 0;
        health = 3;

        color1 = Color.white;

        up = false;
        down = false;
        left = false;
        right = false;
        isFiring=false;
        magazine = 15;


        g_list.add("anim/playerv1.png");
        g_list.add("anim/playerv2.png");
        g_list.add("anim/playerv3.png");

    }


    //function
    public double getX()
    {
        return x;
    }
    public double getY() { return y; }
    public double getW() { return w; }
    public double getH() { return h; }


    public void hit()
        {
            System.out.println(health);
            health--;
        }



    public void update() {
        if(!left && !right && !up && !down)
        {
            s = "anim/playerv2.png";
        }
        distX = gamepanel.mouseX - x;
        distY = y - gamepanel.mouseY;
        dist = (Math.sqrt(distX * distX+distY * distY));

        if (distX>0) angl = Math.acos(distY/(Math.sqrt(distX*distX+distY*distY)));
        if (distX<0) angl =- Math.acos(distY/(Math.sqrt(distX*distX+distY*distY)));


        //смещение героя по игровому полю
        if (up && y > 30) {
            gamepanel.a_shagi.play();
            gamepanel.a_shagi.setVolume();
            gamepanel.a_shagi.timer_play();
            y-=speed;
            isFiring = false;
            s = "anim/playerv3.png";
        }
        if (down && y < gamepanel.Height - h) {
            gamepanel.a_shagi.play();
            gamepanel.a_shagi.setVolume();
            gamepanel.a_shagi.timer_play();
            y+=speed;
            isFiring = false;
            s = "anim/playerv3.png";
        }
        if (left && x > 0) {
            gamepanel.a_shagi.play();
            gamepanel.a_shagi.setVolume();
            gamepanel.a_shagi.timer_play();
            x-=speed;
            isFiring = false;
            s = "anim/playerv3.png";
        }
        if (right && x < gamepanel.Width - w) {
            gamepanel.a_shagi.play();
            gamepanel.a_shagi.setVolume();
            gamepanel.a_shagi.timer_play();
            x+= speed;
            isFiring = false;
            s = "anim/playerv3.png";
        }
        if(up && left || up && right || down && left || down && right)
        {
            double angle = Math.toRadians(45);
            dy = dy * Math.sin(angle);
            dx = dx * Math.cos(angle);
            s = "anim/playerv3.png";

        }
        y += dy;
        x += dx;

        dy = 0;
        dx = 0;

        if(magazine<=0)
        {
            magazine = 0;
            isFiring = false;
        }

        if(isFiring){
          gamepanel.bullets.add(new Bullet());
          gamepanel.a_bul.sound();//звук выстрела
          gamepanel.a_bul.setVolume();//громкость
            s = "anim/playerv2.png";
          isFiring = false;//запрет для срельбы
            magazine--;
        }

    }
    //отрисовка игрока
    public void draw(Graphics2D g)
    {
        Color newColor = new Color(255,120,50);//созд обьект класса цвет
        g.setColor(newColor);//Передаем цвет граф объекту
        g.fillRect(0,0,gamepanel.Width,24);//рисуем прямоуг область
        g.setColor(Color.WHITE);//задаем цвет объекту Color
        Font font = new Font("Cosolas",Font.ITALIC,20);//Создаем объект класса фонт (передаем в констркутор параметры)
        g.setFont(font);//устанавливаем шрифт font

        ((Graphics2D) g).drawString("Health - "+ (int)health,0,20);//отрисовка строчки состояния игрока
        ((Graphics2D) g).drawString("Bullets - "+ magazine,100,20);
        ((Graphics2D) g).drawString("Enemies - "+ gamepanel.enemies.size(),210,20);

        /*g.setColor(color1);
        g.fillOval((int)(x-r),(int)(y-r),2 * r,2 * r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color1.darker());
        g.drawOval((int)(x-r),(int)(y-r),2 * r,2 * r);
        g.setStroke(new BasicStroke(1));*/

        //g.drawImage(img,(int)x,(int)y,null);


        //ВРАЩЕНИЕ
        AffineTransform origXform;//создаем объект класса AffineTransform
        origXform = g.getTransform();//получаем текущее значение
        AffineTransform newXform = (AffineTransform) (origXform.clone());//Клонируем текущее значение
        newXform.rotate(angl,x+20,y+30);//поворот получ. изображения
        g.setTransform(newXform);//ставим трансформированное изображение
        g.drawImage(new ImageIcon(s).getImage(),(int)x,(int)y,null);//отрисовка тестуры игрока
        g.setTransform(origXform);//возвращаем старое значение
    }


}
