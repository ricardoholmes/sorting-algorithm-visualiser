package sortalgorithmvisualiser.mvc;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Sound {
    private static final int SAMPLE_RATE = 8192;

    private static boolean running;
    private static SourceDataLine sdl;
    private static Thread drainThread;

    public static boolean muted = false;

    public static void initialise() throws LineUnavailableException
    {
        running = false;
        if (sdl != null) {
            stopSound();
        }

        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);  
        sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af, 4096);

        sdl.write(new byte[1], 0, 1);
        sdl.start();

        running = true;
        drainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    sdl.drain();
                    while (muted) {
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

    public static void playTone(int hz, int ms, double vol) throws LineUnavailableException 
    {
        if (muted) {
            return;
        }

        int length = (int)(SAMPLE_RATE * ms / 1000.0);
        sdl.write(getDataToWrite(hz, length, vol), 0, length);
    }

    public static void stopSound() {
        running = false;
    }

    private static byte[] getDataToWrite(int hz, int length, double vol) {
        byte[] sin = new byte[length];

        int i;
        double period = (double)SAMPLE_RATE / hz;
        for (i = 0; i < length; i++) {
            double angle = 2.0 * Math.PI * i / period;
            sin[i] = (byte)(int)(Math.sin(angle) * 127f * vol);
        }
        // System.out.println(period + " (" + hz + "): " + Arrays.toString(sin) + " (" + length + ")");


        return sin;
    }
}
