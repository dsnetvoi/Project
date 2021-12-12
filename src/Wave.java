import java.awt.*;

public class Wave {
    //fields
    private int waveMultiplier;
    private int waveNumber;

    private long waveTimer;
    private long waveDelay;
    private long waveTimerDiff;

    private String waveText;



    //constructor

    public Wave()
    {
        waveNumber= 1;
        waveMultiplier = 5;

        waveTimer = 0;
        waveDelay = 5000;
        waveTimerDiff = 0;

        waveText = " W A V E ";
    }

    //functions
    public void createEnemies()
    {
        int enemyCount = waveNumber * waveMultiplier;
        if(waveNumber < waveNumber+1)
        {
            while(enemyCount > 0)
            {
                int rank = 1;
                int type = 1;
                gamepanel.enemies.add(new Enemy(type, rank));
                enemyCount -= type * rank;
            }
        }
        waveNumber++;
    }


    public void update()
    {
        if(gamepanel.enemies.size() == 0 && waveTimer == 0)
        {
            waveTimer = System.nanoTime();
        }
        if(waveTimer > 0)
        {
            waveTimerDiff += (System.nanoTime() - waveTimer)/1000000;
            waveTimer = System.nanoTime();
        }
        if(waveTimerDiff > waveDelay)
        {
            createEnemies();
            waveTimer = 0;
            waveTimerDiff = 0;
        }

    }
    public boolean showWave()
    {
        if(waveTimer !=0)
        {
          gamepanel.a_wave.play();//звук объявления волны
          gamepanel.a_wave.setVolume();//громкость
          return true;
        }
        else
        {
            gamepanel.a_wave.stop();
            return false;
        }

    }

    public void draw(Graphics2D g)
    {
        double divider = waveDelay / 180;
        double alpha = waveTimerDiff / divider;
        alpha = 255 * Math.sin(Math.toRadians(alpha));
        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 0;
        g.setFont(new Font("consolas",Font.PLAIN,20));
        g.setColor(new Color(255,120,50,(int)alpha));
        //TODO change
        String s = "-" +  waveText + waveNumber +" -";
        long length = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, gamepanel.Width/2 - (int) (length/2), gamepanel.Height/2);

    }


}
