import javax.swing.*;
import  java.awt.*;
import  java.awt.geom.AffineTransform;
public class Bullet {
    //Fields
    private double x;
    private double y;

    private double bulletDX;//смещение по x
    private double bulletDY;//смещение по y
    private double distX;//разница по x от мыши до пули
    private double distY;//разница по y от мыши до пули
    private double dist;//расстояние от мыши до пули
    private double h;
    private double w;
    private double speed;

    private Color color;
    Image img = new ImageIcon("C:/Users/user/IdeaProjects/Game1/res/bullet.png").getImage();
    //constructor
    public Bullet()
    {
        x = 20+ gamepanel.player.getX();
        y = 30+ gamepanel.player.getY();
        w = 3;
        h = 10;
        speed = 17;

        distX = gamepanel.aim1.getX()+16 - x;
        distY = y - gamepanel.aim1.getY()-16;
        dist = Math.sqrt(distX * distX + distY * distY);//расстояние от мыши до пули

        bulletDX = (distX/dist) * speed;//смещение по x
        bulletDY = (distY/dist) * speed;//смещение по y

        color = Color.ORANGE;
    }
    //functions

    public double getX(){return x;}
    public double getY(){return y;}
    public double getH(){return h;}
    public double getW(){return w;}

    public boolean remove()
    {
        if(y < 0 && y > gamepanel.Height && x < 0 && x > gamepanel.Width){
            return true;
        }
        return false;

    }

    //смещение пули
    public void update()
    {
        y = y - speed * distY/(Math.sqrt(distX*distX+distY*distY));
        x = x + speed * distX/(Math.sqrt(distX*distX+distY*distY));
    }
    //отрисовка
    public void draw(Graphics2D g)
    {
        //g.drawImage(img,(int)x,(int)y,null);
       /* g.setColor(color);
        g.fillOval((int) x, (int) y, r, 2 * r);*/

        AffineTransform origXform;//создаем объект класса AffineTransform
        origXform = g.getTransform();//получаем текущее значение
        AffineTransform newXform = (AffineTransform)(origXform.clone());
        if (distX>0)  newXform.rotate(Math.acos(distY/(Math.sqrt(distX*distX+distY*distY))),x,y);
        if (distX<0)  newXform.rotate(-Math.acos(distY/(Math.sqrt(distX*distX+distY*distY))),x,y);
        g.setTransform(newXform);
        g.drawImage(img,(int)x,(int)y,null);
        g.setTransform(origXform);

    }
    //проверка где пуля
    public boolean remove_f()
    {
        //если вылетела
        if(y < 20 || y>gamepanel.Height || x<0 || x>gamepanel.Width)
        {
            return true;

        }
        return false;
    }
}

