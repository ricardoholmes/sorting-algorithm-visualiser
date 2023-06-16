package sorters;

import mvc.Controller;
import mvc.Model;

public abstract class Sorter {
    protected Controller controller;
    protected Model model;
    protected boolean shouldStop;

    public final void initialise(Controller c, Model m) {
        controller = c;
        model = m;
    }

    public abstract String getName();

    public abstract void sort(int delay);

    protected final void sleep(int delay) {
        for (int i = 0; i < delay; i++) {
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
