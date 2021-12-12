import javax.swing.*;

public class GameStart
{

    public static void main( String[] args)
    {
      gamepanel panel = new gamepanel();
      JFrame startFrame = new JFrame("Waves");//Изменить
      startFrame.setResizable(false);
      startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//константы по опред на выход при нажатии крестика

      startFrame.setContentPane(panel);
      startFrame.pack();
      startFrame.setLocationRelativeTo(null);
      startFrame.setVisible(true);
      panel.start();

    }


}
