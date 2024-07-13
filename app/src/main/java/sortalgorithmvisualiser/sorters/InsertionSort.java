package sortalgorithmvisualiser.sorters;

public class InsertionSort extends Sorter {
    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public void sort(double delay, boolean sortAscending) {
        shouldStop = false;
        for (int i = 1; i < sizeOfArray; i++) {
            int currentValue = controller.getNumAtIndex(i);
            int j = i - 1;

            while (j >= 0 && !inOrder(controller.getNumAtIndex(j), currentValue, sortAscending)) {
                if (shouldStop) {
                    return;
                }

                sleep(delay);
                controller.swapIndexes(j, j + 1);
                j--;
            }
        }
    }
}
