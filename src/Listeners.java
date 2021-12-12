
import java.awt.event.*;

import java.util.TimerTask;

public class Listeners implements KeyListener, MouseListener, MouseMotionListener {
    private java.util.Timer timerr = new java.util.Timer();
    //проверка нажатой клавиши
    private boolean isFiring_on; // дослать патрон
    static TimerTask tTask;
    private int timerIndex;

    public void initTask() {
        tTask = new TimerTask()
        {
            public void run()
            {
                Player.magazine = 15;
            }
        };
    }
    public void reloadTimer() {
        initTask();
        if(gamepanel.state.equals(gamepanel.STATES.PLAY)){
        if (timerIndex == 0) {
            // запускаем таймер с задержкой 2000 миллисекунд
            timerr.schedule(tTask, 2000);
            gamepanel.a_reload1.sound();//звук перезарядки
            gamepanel.a_reload1.setVolume();//громкость

        }}
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            Player.up = true;
        }
        if (key == KeyEvent.VK_S) {
            Player.down = true;
        }
        if (key == KeyEvent.VK_A) {
            Player.left = true;
        }
        if (key == KeyEvent.VK_D) {
            Player.right = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            if(isFiring_on)//если патрон заряжен
            Player.isFiring = true;//стрельба разрешена
            isFiring_on = false;//нет патрона
        }
    }
    //проверка зажатой клавиши
    public void keyReleased(KeyEvent e){
    int key = e.getKeyCode();
        if(key == KeyEvent.VK_W)
        {
            Player.up = false;
        }
        if(key == KeyEvent.VK_S)
        {
            Player.down = false;
        }
        if(key == KeyEvent.VK_A)
        {
            Player.left = false;
        }
        if(key == KeyEvent.VK_D)
        {
            Player.right = false;
        }
        if (key == KeyEvent.VK_SPACE)
        {
            Player.isFiring = false;
            isFiring_on = true;//патрон в патроннике
        }
        if(key == KeyEvent.VK_ESCAPE)
        {
            gamepanel.state = gamepanel.STATES.MENUE;
        }
        if(key == KeyEvent.VK_R)
        {
            if(gamepanel.state.equals(gamepanel.STATES.PLAY))//если мы в режиме игры, то разрешаем перезарядку
                Player.magazine = 0;
                reloadTimer();
        }
    }



    public void keyTyped(KeyEvent e)
    {

    }


    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressed(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            if(gamepanel.state.equals(gamepanel.STATES.PLAY))//если мы в режиме игры, то разрешаем стрелть
            gamepanel.player.isFiring = true;
            gamepanel.leftMouse = true;
            if(gamepanel.state.equals(gamepanel.STATES.MENUE))//если мы в режиме игры, то разрешаем стрелть
            Menue.click = true;//нажатие лкм в меню
        }
    }


    public void mouseReleased(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            gamepanel.player.isFiring = false;
            gamepanel.leftMouse = false;
            if(gamepanel.state.equals(gamepanel.STATES.MENUE))//если мы в режиме игры, то разрешаем стрелть
            Menue.click = false;//нажатие лкм в меню
        }
    }


    public void mouseEntered(MouseEvent e)
    {

    }


    public void mouseExited(MouseEvent e)
    {

    }


    public void mouseDragged(MouseEvent e)
    {
        gamepanel.mouseX = e.getX();
        gamepanel.mouseY = e.getY();
    }


    public void mouseMoved(MouseEvent e)
    {
        gamepanel.mouseX = e.getX();
        gamepanel.mouseY = e.getY();
    }
}
