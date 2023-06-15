package sorters;

import mvc.Controller;
import mvc.Model;

public class BubbleSort implements ISorter {
    Controller controller;
    Model model;

    @Override
    public void initialise(Controller c, Model m) {
        controller = c;
        model = m;
    }
    
    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    public void sort(int delay) {
        boolean sorted = false;
        int iterations = 0;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < model.getArrayLength() - 1 - iterations; i++) {
                if (controller.getNumAtIndex(i) > controller.getNumAtIndex(i + 1)) {
                    controller.swapIndexes(i, i+1);
                    sorted = false;

                    // delay
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) { }
                }
            }
            iterations++;
        }
    }
}
