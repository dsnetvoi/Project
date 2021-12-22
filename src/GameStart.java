import javax.swing.*;
//главное окно
public class GameStart
{
    public static void main( String[] args)
    {
      gamepanel panel = new gamepanel();//cоздадим объект панель
      JFrame startFrame = new JFrame("Waves");// создание окна с названием
      startFrame.setResizable(false);// запрет на изменение формы окна
      startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//константы по опред на выход при нажатии крестика
      startFrame.setContentPane(panel);// перенос в фрейм панели с GamePanel
      startFrame.pack();//фрейм по размерам занимал столько сколькое его компоненты
      startFrame.setLocationRelativeTo(null);//устанавливаем позицию окна по центру
      startFrame.setVisible(true);// окно видемо
      panel.start();// заускаем поток панели
    }
}
