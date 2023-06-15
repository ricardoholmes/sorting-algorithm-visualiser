package sorters;

import mvc.Controller;
import mvc.Model;

public class BogoSort implements ISorter {
    Controller controller;
    Model model;

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
        while (!controller.isSorted()) {
            controller.shuffle();

            // delay
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) { }
        }
    }
}
