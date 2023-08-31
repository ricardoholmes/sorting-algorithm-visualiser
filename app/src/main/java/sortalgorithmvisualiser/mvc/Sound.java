package sortalgorithmvisualiser.mvc;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Sound {
    private static final int SAMPLE_RATE = 4096;

    private static boolean running;
    private static SourceDataLine sdl;
    private static Thread drainThread;

    public static void initialise() throws LineUnavailableException
    {
        running = false;
        if (sdl != null) {
            stopSound();
        }

        AudioFormat af = new AudioFormat(SAMPLE_RATE,8,1,true,false);  
        sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);

        sdl.start();
        sdl.write(new byte[1], 0, 1);
        sdl.flush();

        running = true;
        drainThread = new Thread(new Runnable() {
            @Override
            public void run() {
            while (running) {
                sdl.drain();
                while (OptionsPanel.isMuted()) {
                    sdl.flush();
                }
            }
            sdl.flush();
            sdl.stop();
            sdl.close();
            }
        });
        drainThread.start();
    }

    public static void playTone(int hz, int ms) throws LineUnavailableException 
    {
        playTone(hz, ms, 1.0);
    }

    public static void playTone(int hz, int ms, double vol) throws LineUnavailableException 
    {
        int length = (int)(SAMPLE_RATE * ms / 1000.0);
        sdl.write(getDataToWrite(hz, ms, length, vol), 0, length);
    }

    public static void stopSound() {
        running = false;
    }

    private static byte[] getDataToWrite(int hz, int ms, int length, double vol) {
        byte[] sin = new byte[length];
        for (int i = 0; i < sin.length; i++) {
            double period = (double)SAMPLE_RATE / hz;
            double angle = 2.0 * Math.PI * i / period;
            sin[i] = (byte)(Math.sin(angle) * 127f * vol);
        }

        return sin;
    }
}
