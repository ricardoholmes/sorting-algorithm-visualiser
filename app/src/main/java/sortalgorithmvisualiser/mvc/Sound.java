package sortalgorithmvisualiser.mvc;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Sound {
    private static float SAMPLE_RATE = 8000f;
    private static boolean running = false;
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
        drainThread = new Thread(() -> {
            while (running) {
                sdl.drain();
            }
            sdl.flush();
            sdl.stop();
            sdl.close();
        });
        drainThread.start();
    }

    public static void playTone(int hz, int millis) throws LineUnavailableException 
    {
        playTone(hz, millis, 1.0);
    }

    public static void playTone(int hz, int millis, double vol) throws LineUnavailableException 
    {
        byte[] buf = new byte[1];
        sdl.start();
        for (int i=0; running && i < millis * 8; i++) {
            double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
            buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);

            sdl.write(buf, 0, 1);
        }
    }

    public static void stopSound() {
        running = false;
    }
}
