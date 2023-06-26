package sortalgorithmvisualiser.sorters;

import sortalgorithmvisualiser.mvc.Controller;
import sortalgorithmvisualiser.mvc.Model;

public abstract class Sorter {
    protected Controller controller;
    protected Model model;
    protected boolean shouldStop;

    public final void initialise(Controller c, Model m) {
        controller = c;
        model = m;
    }

    public abstract String getName();

    public abstract void sort(double delay, boolean sortAscending);

    /*
     * Returns whether if placed in order "a, b", the numbers would be in the correct order,
     * as stated by sortAscending
     */
    protected final boolean inOrder(int a, int b, boolean sortAscending) {
        // sortAscending AND !(a > b)
        // OR
        // !sortAscending AND (a > b)
        // THEREFORE
        // sortAscending XOR a > b
        return sortAscending ^ (a > b);
    }

    protected final void sleep(double delay) {
        long millis = (long)delay;
        int nanos = (int)((delay % 1) * 1_000_000);

        try {
            Thread.sleep(0, nanos);
        } catch (InterruptedException e) { }

        for (int i = 0; i < millis; i++) {
            if (shouldStop) {
                return;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) { }
        }
    }

    public final void stop() {
        shouldStop = true;
    }
}
