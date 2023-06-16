package sorters;

import mvc.Controller;
import mvc.Model;

public abstract class Sorter {
    Controller controller;
    Model model;
    protected boolean shouldStop;

    public final void initialise(Controller c, Model m) {
        controller = c;
        model = m;
    }

    public abstract String getName();

    public abstract void sort(int delay);

    public final void stop() {
        shouldStop = true;
    }

    public final boolean isStopped() {
        return shouldStop;
    }
}
