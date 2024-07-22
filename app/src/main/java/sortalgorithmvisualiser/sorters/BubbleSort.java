package sortalgorithmvisualiser.sorters;

public class BubbleSort extends Sorter {
    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    public void sort() {
        shouldStop = false;
        boolean sorted = false;
        int iterations = 0;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < sizeOfArray - 1 - iterations; i++) {
                if (shouldStop) {
                    return;
                }

                if (inOrder(controller.getNumAtIndex(i+1), controller.getNumAtIndex(i), sortAscending)) {
                    sleep(delay);

                    controller.swapIndexes(i, i+1);
                    sorted = false;
                }
            }
            iterations++;
        }
    }
}
