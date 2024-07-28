package sortalgorithmvisualiser.mvc;

import java.util.Random;

public class Oscillator {
    public enum Wave {
        Triangle,
        Sine,
        Square,
        Sawtooth,
        Random, // Random must be last in the enum
    }

    public static Wave wave = Wave.Triangle;

    public static double attack = 0.05; // percentage of duration
    public static double hold = 0.45; // percentage of duration
    public static double decay = 0.1;    // percentage of duration
    public static double sustain = 0.9;  // percentage of amplitude
    public static double release = 0.5;  // percentage of duration

    private double _freq;
    private int _start;
    private int _end;
    private int _duration;

    public Oscillator(double freq, int start, int duration) {
        _freq = freq;
        _start = start;
        _duration = duration;
        _end = start + duration;
    }

    private static double triangleWave(double t) {
        double x = ((t - 0.25) % 1.0) - 0.5;
        return 4.0 * Math.abs(x) - 1.0;
    }

    private static double sineWave(double t) {
        return Math.sin(2 * Math.PI * t);
    }

    private static double squareWave(double t) {
        return Math.signum(sineWave(t));
    }

    private static double sawtoothWave(double t) {
        return 2 * (t - Math.floor(0.5 + t));
    }

    public static double wave(double t) {
        boolean isRandom = (wave == Wave.Random);
        if (isRandom) {
            Wave[] waveTypes = Wave.values();
            int waveIndex = new Random().nextInt(waveTypes.length - 1);
            wave = waveTypes[waveIndex];
        }

        double value = switch (wave) {
            case Triangle -> triangleWave(t);
            case Sine -> sineWave(t);
            case Square -> squareWave(t);
            case Sawtooth -> sawtoothWave(t);
            default -> 0;
        };

        if (isRandom) {
            wave = Wave.Random; // reset it to random
        }

        return value;
    }

    public double envelope(int i) {
        double x = ((double)i) / _duration;

        if (x > 1) {
            x = 1;
        }

        if (x < attack) {
            return 1.0 / attack * x;
        }
        x -= attack;

        if (x < decay) {
            return 1.0 - x / decay * (1.0 - sustain);
        }
        x -= decay;

        if (x < hold) {
            return sustain;
        }
        x -= hold;

        if (x < release) {
            return sustain / release * (release - x);
        }

        return 0;
    }

    public void mix(double[] data, int p) {
        for (int i = 0; i < data.length; i++) {
            if (p + i < _start) {
                continue;
            }
            
            if (p + i >= _end) {
                break;
            }

            int relativeTime = (p + i - _start);
            data[i] += envelope(relativeTime) * wave((double)relativeTime / Sound.SAMPLE_RATE * _freq);
        }
    }

    public int getStart() {
        return _start;
    }

    public boolean isDone(int p) {
        return (p >= _end);
    }
}
