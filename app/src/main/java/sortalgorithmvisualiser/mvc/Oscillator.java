package sortalgorithmvisualiser.mvc;

public class Oscillator {
    private double _freq;
    private int _start;
    private int _end;
    private int _duration;

    public Oscillator(double freq, int start) {
        int duration = Sound.SAMPLE_RATE / 8;
        _freq = freq;
        _start = start;
        _duration = duration;
        _end = start + duration;
    }

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

    public static double wave(double t) {
        return triangleWave(t);
    }

    public double envelope(int i) {
        double x = ((double)i) / _duration;

        if (x > 1)
            x = 1;

        double attack = 0.025; // percentage of duration
        double decay = 0.1;    // percentage of duration
        double sustain = 0.9;  // percentage of amplitude
        double release = 0.5;  // percentage of duration

        if (x < attack)
            return 1.0 / attack * x;

        if (x < attack + decay)
            return 1.0 - (x - attack) / decay * (1.0 - sustain);

        if (x < 1.0 - release)
            return sustain;

        return sustain / release * (1.0 - x);
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
