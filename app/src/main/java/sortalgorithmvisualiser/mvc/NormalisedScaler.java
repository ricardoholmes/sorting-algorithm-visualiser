package sortalgorithmvisualiser.mvc;

// scalers to apply to normalised values (i.e. in range 0<=x<=1)
public enum NormalisedScaler {
    Linear, // y = x
    Square, // y = x^2
    Cube, // y = x^3
    Exponential, // y = 2^x - 1
    SquareRoot, // y = x^{1/2} -- inverse of square
    CubeRoot, // y = x^{1/3} -- inverse of cube
    Logarithmic; // y = log_2(x + 1) -- inverse of exponential

    public double applyScaler(double x) {
        return switch (this) {
            case Linear -> x;
            case Square -> x * x;
            case Cube -> x * x * x;
            case Exponential -> Math.pow(2, x);
            case SquareRoot -> Math.sqrt(x);
            case CubeRoot -> Math.cbrt(x);
            case Logarithmic -> Math.log(x + 1) / Math.log(2);
            default -> x; // default to linear
        };
    }
}
