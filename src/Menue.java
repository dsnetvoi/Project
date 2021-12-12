import java.awt.*;
import javax.swing.*;

public class Menue {
    public static boolean click;
    private int n ;
    private Color color1;
    private double x;
    private double y;
    private double w;
    private double h;

    //  объявление кнопок
    ButtMenue button1 = new ButtMenue(307,30,396,100,"C:/Users/user/IdeaProjects/Game1/res/but1.png","Играть");
    ButtMenue button2 = new ButtMenue(307,180,396,100,"C:/Users/user/IdeaProjects/Game1/res/but1.png","Правила");
    ButtMenue button3 = new ButtMenue(307,320,396,100,"C:/Users/user/IdeaProjects/Game1/res/but1.png","Выход");
    // вызов метода для прорисовки кнопки
    public void draw(Graphics2D g) {
        button1.draw(g);
        button2.draw(g);
        button3.draw(g);

    }
    public Menue()
    {
        x= 307;
        y= 20;
        w= 396;
        h= 100;
        n= 3;
    }
    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    public double getW()
    {
        return w;
    }
    public double getH()
    {
        return h;
    }

    public void moveButt(ButtMenue e){
        if (gamepanel.mouseX >e.getX() && gamepanel.mouseX <e.getX() + e.getW()  &&
                gamepanel.mouseY >e.getY() && gamepanel.mouseY < e.getY()+ e.getH()) {// если курсор попал в кнопку
            e.s = "C:/Users/user/IdeaProjects/Game1/res/but2.png";
            if(e.equals(button1)) {e.f="играть";
                if (click) {  //клик ЛКМ
                    gamepanel.state = gamepanel.STATES.PLAY; //переход в игру
                    click = false;
                }
            }
            if(e.equals(button2))
            {
                e.f="управление";
                if (click)
                {
                    gamepanel.pravmenue = true;
                    gamepanel.buttmenue = false;
                }

            }
            if(e.equals(button3)) {e.f="выход";
                if(click){
                    System.exit(0);
                }
            }
        }
        else{
            e.s = "C:/Users/user/IdeaProjects/Game1/res/but1.png";
            if(e.equals(button1)) {e.f="ИГРАТЬ";}
            if(e.equals(button2)) {e.f="УПРАВЛЕНИЕ";}
            if(e.equals(button3)) {e.f="ВЫХОД";}
        }
    }
    class ButtMenue{
        //  нач координаты и размер объекта
        private double x;//координа х обьекта
        private double y;//координа y
        private double w ; //ширина обьекта
        private double h ; // высота объекта

        public Color color1;// цвет
        public String f;// надпись над кнопкой
        public String s; // строка адреса картинки
        // Constructor
        public ButtMenue(int x,int y,int w,int h,String s,String f)
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
        public void draw(Graphics2D g)
        {
            g.drawImage( new ImageIcon(s).getImage(), (int)x,(int) y, null);//отрисовываем элемент в координатах
            g.setColor(color1);//задаем цвет объекту Соlor
            Font font = new Font("Consolas",Font.BOLD,55);//Создём объект класса фонт (передаем в конструктор параметры)
            g.setFont(font);//устанвливаем наш шрифт

            long length = (int) g.getFontMetrics().getStringBounds(f,g).getWidth();// длина надписи в пиксилях
            g.drawString(f,(int)(x+w/2) - (int)(length / 2),(int)y+(int)(h/ 3)*2);// рисуем строчку в центре

        }
    }

}





