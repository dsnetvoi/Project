import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    private String track;//адрес з.файла
    private Clip clip = null;//ссылка на объеут класса
    private FloatControl volumeC = null;//контролер громкости
    private double wt; //уровень громкости
    private boolean pl_audio;//состояние воспроизведения

    private long timer_p = 0;//начальное время для таймера
    private long timer_f = 0;//конечное время
    private long timer_d;//длительность трека (милисек)

    public Audio(String track, double wt)
    {
        this.track = track;
        this.wt = wt;
    }
    public Audio(String track, double wt, long timer_d)
    {
        this.timer_d = timer_d;
        this.track = track;
        this.pl_audio = false;
        this.wt = wt;
    }



    public void sound()
    {
        File f = new File(this.track);//передаем звуковой файл в f
        //поток для записи и считывания
        AudioInputStream tr = null; //объект потока AudioInputStream пуст
        try {
            tr = AudioSystem.getAudioInputStream(f);//Получаем AudioInputStream (файл)
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();//получаем реализацию интерфейса Clip
            clip.open(tr);//загружаем наш звуковой поток в Clip
            //Получаем контроллер громкости
            volumeC = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            clip.setFramePosition(0);//устанавливаем указатель на старт
            clip.start();//старт
            }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //одиночное проигрывание с stop()
    public void play()
    {

        File f = new File(this.track);//передаем звуковой файл в f
        //поток для записи и считывания
        AudioInputStream tr = null; //объект потока AudioInputStream пуст
        try {
            tr = AudioSystem.getAudioInputStream(f);//Получаем AudioInputStream (файл)
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();//получаем реализацию интерфейса Clip
            clip.open(tr);//загружаем наш звуковой поток в Clip
            //Получаем контроллер громкости
            volumeC = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            if(!this.pl_audio)
            {
                clip.setFramePosition(0);//указатель на старт
                clip.start();//старт
                this.pl_audio = true;//воспроизведение
            }
        }
        catch(LineUnavailableException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        clip.stop();
        clip.close();//закрываем
        this.pl_audio = false;

    }

    //уровень громкости
    public void setVolume()
    {
        if (wt < 0) wt = 0;
        if (wt > 1) wt = 1;
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        volumeC.setValue((max-min)*(float)wt+min);
    }

    //повтор (работает с треками запущенными методом play())
    public void repeat()
    {
        if(this.pl_audio)
            clip.loop(10);//повтор n раз
    }

    //таймер звука (для треков запущ. play() и конструктор с timer_d)
    public void timer_play()
    {
        if (timer_p == 0)//если таймер не запущен
        {
            timer_p = System.currentTimeMillis();//получаем теукщее время милисек
            timer_f = timer_p + timer_d;//конечное время трека
        }
        if(timer_f <= System.currentTimeMillis())//если время звучания меньше текущего времени
        {
            this.stop();
            timer_p = 0;//обнуление счетчика
        }
    }

}
