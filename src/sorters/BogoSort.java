package sorters;

import mvc.Controller;
import mvc.Model;

public class BogoSort implements ISorter {
    Controller controller;
    Model model;
    private boolean shouldStop;

    @Override
    public void initialise(Controller c, Model m) {
        controller = c;
        model = m;
    }

    @Override
    public String getName() {
        return "Bogo Sort";
    }

    @Override
    public void sort(int delay) {
        shouldStop = false;
        while (!controller.isSorted()) {
            if (shouldStop) {
                return;
            }

            controller.shuffle();

            // delay
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) { }
        }
    }

    public void stop() {
        shouldStop = true;
    }

    public boolean isStopped() {
        return shouldStop;
    }
}
