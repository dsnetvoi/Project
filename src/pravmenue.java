import javax.swing.*;
import java.awt.*;

public class pravmenue {
    //  нач координаты и размер объекта
    private double x;//координа х обьекта
    private double y;//координа y
    private double w ; //ширина обьекта
    private double h ; // высота объекта
    Image img1 = new ImageIcon("C:/Users/user/IdeaProjects/Game1/res/pravmenue.png").getImage();

    public Color color1;// цвет

    public String f;// надпись над кнопкой

    public String s; // строка адреса картинки

    // Constructor
    public pravmenue(int x,int y,int w,int h,String s,String f)
    {
        this.x =x;// нач координаты героя
        this.y = y;
        this.w = w;
        this.h = h;
        this.f = f;
        this.s = s;
        color1 = Color.WHITE;



    }

    //  гетеры
    public double getX(){
        return  this.x;
    }
    public double getY(){
        return  this.y;
    }
    public double getW(){
        return  this.w;
    }
    public double getH(){
        return  this.h;
    }




    // отрисовка объекта
    public void draw(Graphics2D g){


        g.drawImage( new ImageIcon(s).getImage(), (int)x,(int) y, null);//отрисовываем элемент в координатах
        g.setColor(color1);//задаем цвет объекту Соlor
        Font font1 = new Font("Сonsolas",Font.BOLD,15);//Создём объект класса фонт (передаем в конструктор параметры)
        g.setFont(font1);//устанвливаем наш шрифт

        g.drawImage(img1,(int)0,(int)0,null);;//отрисовываем элемент в координатах
        long length = (int) g.getFontMetrics().getStringBounds(f,g).getWidth();// длина надписи в пиксилях
        g.drawString(f,(int)(x+w/2) - (int)(length / 2),(int)y+(int)(h/ 3)*2);// рисуем строчку в центре

    }
}

