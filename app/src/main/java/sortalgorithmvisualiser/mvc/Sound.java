package sortalgorithmvisualiser.mvc;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Sound {
    public static final int SAMPLE_RATE = 44100;
    public static final int MIN_DELAY_MS = 5;
    public static final double SUSTAIN = 2;

    public static boolean muted = false;

    public static int maxOscillators = 256;
    private static ArrayList<Oscillator> oscillators = new ArrayList<>();

    public static int time = 0; // in sound sample units

    private static boolean running;
    private static SourceDataLine sdl;
    private static Thread drainThread;

    public static void initialise() throws LineUnavailableException
    {
        time = 0;
        stopSound();

        if (sdl == null) {
            AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af, 4096);
        }
        else {
            while (sdl.isRunning());
        }

        sdl.start();
        running = true;
        drainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    update();
                }
                sdl.drain();
                sdl.stop();
            }
        });
        drainThread.start();
    }

    public static void playTone(double freq, int delay) throws LineUnavailableException 
    {
        delay = Integer.max(MIN_DELAY_MS, delay);
        addOscillator(
            freq,
            time,
            time,
            (int)(delay / 1000.0 * SUSTAIN * SAMPLE_RATE)
        );
    }

    public static void stopSound() {
        running = false;
    }

    public static void addOscillator(double freq, int p, int pStart, int duration) {
        int oldest = 0;
        int oldestTime = Integer.MAX_VALUE;

        for (int i = 0; i < oscillators.size(); i++) {
            if (oscillators.get(i).isDone(p)) {
                oscillators.set(i, new Oscillator(freq, pStart, duration));
                return;
            }

            if (oscillators.get(i).getStart() < oldestTime) {
                oldest = i;
                oldestTime = oscillators.get(i).getStart();
            }
        }

        if (oscillators.size() < maxOscillators) {
            oscillators.add(new Oscillator(freq, pStart, duration));
        }
        else {
            oscillators.set(oldest, new Oscillator(freq, pStart, duration));
        }
    }

    public static void update() {
        if (sdl.available() == 0) {
            return;
        }

        int len = sdl.available();

        if (muted || !running) {
            sdl.write(new byte[len], 0, len);
            return;
        }

        double[] wave = new double[len];

        int waveCount = 0;

        for (int i = 0; i < oscillators.size(); i++) {
            Oscillator oscillator = oscillators.get(i);
            if (!oscillator.isDone(time)) {
                oscillator.mix(wave, time);
                waveCount++;
            }
        }

        if (waveCount == 0) {
            sdl.write(new byte[len], 0, len);
            return;
        }
        else {
            double volSelected = OptionsPanel.getVolume();

            double vol = Arrays.stream(wave).max().getAsDouble(); // max amplitude

            byte[] stream = new byte[len];
            for (int i = 0; i < len; i++) {
                short v = (short)(127.0 * wave[i]);
                v *= volSelected / vol;

                if (v > 127) {
                    v = 127;
                }
                else if (v < -127) {
                    v = -127;
                }

                stream[i] = (byte)v;
            }

            sdl.write(stream, 0, len);
        }

        time += len;
    }
}
