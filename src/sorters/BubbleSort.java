package sorters;

public class BubbleSort extends Sorter {
    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    public void sort(int delay) {
        shouldStop = false;
        boolean sorted = false;
        int iterations = 0;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < model.getArrayLength() - 1 - iterations; i++) {
                if (shouldStop) {
                    return;
                }

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
