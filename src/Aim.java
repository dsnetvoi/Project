import javax.swing.*;
import java.awt.*;

//прицел//
public class Aim {
    //нач координаты и размер объекта
    private double x;
    private double y;
    private double h;
    private double w;
    private double dx;
    private double dy;
    private String img;

    public Aim( int x, int y, int w, int h, String img, int dx, int dy)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.img = img;
        this.dx = dx;
        this.dy = dy;

    }
    public Rectangle getRect()
    {
        return new Rectangle((int) x,(int) y,(int) w,(int) h); //возвращаем конструктор с размером объекта
    }
    //геттеры
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

    //Смещение
    public void update()
    {
        this.x = gamepanel.mouseX + dx;
        this.y = gamepanel.mouseY + dy;
    }

    //Отрисовка
    public void draw(Graphics2D g)
    {
        Image im = new ImageIcon(img).getImage();
        g.drawImage(im,(int)this.x,(int)this.y,null);//отрисовка элемента в координатах
    }
}


