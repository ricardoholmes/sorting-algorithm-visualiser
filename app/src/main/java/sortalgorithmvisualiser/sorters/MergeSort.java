package sortalgorithmvisualiser.sorters;

public class MergeSort extends Sorter {
    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public void sort(double delay, boolean sortAscending) {
        shouldStop = false;
        recursiveSort(0, sizeOfArray, delay, sortAscending);
    }

    // interval [start, end)
    void recursiveSort(int start, int end, double delay, boolean sortAscending) {
        if (start + 1 == end) {
            return;
        }

        int middle = start + ((end - start) / 2);

        recursiveSort(start, middle, delay, sortAscending);
        recursiveSort(middle, end, delay, sortAscending);

        int leftIndex = start;
        int rightIndex = middle;

        while (leftIndex < rightIndex && rightIndex < end) {
            if (shouldStop) {
                return;
            }

            int leftNum = controller.getNumAtIndex(leftIndex);
            int rightNum = controller.getNumAtIndex(rightIndex);
            if (inOrder(rightNum, leftNum, sortAscending)) {
                sleep(delay);

                controller.moveNumber(rightIndex, leftIndex);
                rightIndex++;
                leftIndex++;
            }
            else {
                leftIndex++;
            }
        }
    }
}
