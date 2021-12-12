import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Enemy {
    //fields
    private double x;
    private double y;
    private double h;
    private double w;

    private double angl;//угол поворота
    private double distX;//разница по x от Player
    private double distY;//
    private double dist;//расстояие от Player
    private String s;//строка адреса для анимирования
    private double speed;
    private double dx;
    private double dy;
    private Color color;
    private double health;

    private int type;
    private int rank;

    ArrayList <String> z_list = new ArrayList<>();//кол картинок для анимации движ
    public int i_anim = 0;

    Image img = new ImageIcon("res/enemy.png").getImage();


    Timer timer1 = new Timer (250, new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            i_anim++;
            s = z_list.get(i_anim);//строка адреса картинки
            if (i_anim >= z_list.size() - 1)
            {
               if(i_anim >= 2){i_anim = 0;}
               else
               i_anim = z_list.size()-1;

            }
        }
    });

    public Rectangle getRect()
    {
        return new Rectangle((int) x,(int) y,45,68);//возврат конструктор с размером объекта
    }
    //constructor
    public Enemy(int type,int rank)
    {
        this.type = type;
        this.rank = rank;

        z_list.add("anim/z1.png");
        z_list.add("anim/z2.png");
        z_list.add("anim/z3.png");


        i_anim = 0;//счтетчик кадров


        switch(type)
        {
            case(1): color=Color.GREEN;
                switch (rank){
                case(1):

                    x = Math.random() * gamepanel.Width;
                    y = 50;
                    h = 45;
                    w = 68;

                    speed = 6;

                    health =1;

                    double angle = Math.toRadians(Math.random()*360);
                    dx = Math.sin(angle) * speed;
                    dy = Math.cos(angle) * speed;
            }
        }
    }
    //functions
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public double getH()
    {
        return h;
    }
    public double getW()
    {
        return w;
    }



    public boolean remove_f()
    {
        if(health <= 0)
        {
            return  true;
        }
        return false;
    }


    public void hit()
    {
        health--;
    }

    public void update()
    {
        timer1.start();
        distX = gamepanel.player.getX() -x;
        distY = y - gamepanel.player.getY();
        dist = (Math.sqrt(distX * distX+distY * distY));

        if (distX>0) angl = Math.acos(distY/(Math.sqrt(distX*distX+distY*distY)));
        if (distX<0) angl =- Math.acos(distY/(Math.sqrt(distX*distX+distY*distY)));
        x += dx;
        y += dy;


        if(x < 0 && dx < 0)dx = -dx;
        if(x > gamepanel.Width-50 && dx > 0) dx = -dx;
        if(y < 30 && dy < 0) dy = -dy;
        if(y > gamepanel.Height-50 && dy > 0) dy = -dy;
    }
    public void draw(Graphics2D g)
    {
       /* g.setColor(color);
        g.fillOval((int)(x - r),(int)(y - r),2 * r,2 * r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color.darker());
        g.drawOval((int)(x - r),(int)(y - r),2 * r,2 * r);
        g.setStroke(new BasicStroke(1));*/
       // g.drawImage(img,(int)x,(int)y,null);//

        AffineTransform origXform;//создаем объект класса AffineTransform
        origXform = g.getTransform();//получаем текущее значение
        AffineTransform newXform = (AffineTransform) (origXform.clone());//Клонируем текущее значение
        newXform.rotate(angl,x+29,y+25);//поворот получ. изображения
        g.setTransform(newXform);//ставим трансформированное изображение
        g.drawImage(new ImageIcon(s).getImage(),(int)x,(int)y,null);//отрисовка тестуры игрока
        g.setTransform(origXform);//возвращаем старое значение
    }

}
