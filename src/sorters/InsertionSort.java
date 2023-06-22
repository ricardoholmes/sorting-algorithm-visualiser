
package sorters;

public class InsertionSort extends Sorter {
    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public void sort(int delay, boolean sortAscending) {
        shouldStop = false;
        for (int i = 1; i < model.getArrayLength(); i++) {
            int currentValue = controller.getNumAtIndex(i);
            int j = i - 1;

            while (j >= 0 && !inOrder(controller.getNumAtIndex(j), currentValue, sortAscending)) {
                if (shouldStop) {
                    return;
                }
                controller.swapIndexes(j, j + 1);
                j--;

                sleep(delay);
            }
        }
    }
}
