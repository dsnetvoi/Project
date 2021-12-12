import javax.swing.*;
import  java.awt.*;
public class GameBack {

    Image img = new ImageIcon("C:/Users/user/IdeaProjects/Game1/res/unnamed.png").getImage();

    //Functions
    public void update()
    {
    }
    public void draw(Graphics2D g)
    {
        Color bacColor = new Color(255, 182, 37, 213);//созд обьект клсса цвет
        g.setColor(bacColor);// передаём цвет граф объекту

        if (gamepanel.state.equals(gamepanel.STATES.MENUE)) g.fillRect(0, 0, gamepanel.Width, gamepanel.Height);//отрисовываем элемент в координатах
        if (gamepanel.state.equals(gamepanel.STATES.PLAY)) g.drawImage(img,(int)0,(int)0,null);//отрисовываем элемент в координатах
    }
}
