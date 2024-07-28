package sortalgorithmvisualiser.sorters;

public class InsertionSort extends Sorter {
    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public void sort() {
        for (int i = 1; i < sizeOfArray; i++) {
            int currentValue = controller.getNumAtIndex(i);
            int j = i - 1;

            while (j >= 0 && !inOrder(controller.getNumAtIndex(j), currentValue)) {
                if (shouldStop) {
                    return;
                }

                controller.swapIndexes(j, j + 1);
                j--;
            }
        }
    }
}
