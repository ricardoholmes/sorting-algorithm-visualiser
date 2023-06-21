package sorters;

public class MergeSort extends Sorter {
    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public void sort(int delay, boolean sortAscending) {
        shouldStop = false;
        recursiveSort(0, model.getArrayLength(), delay, sortAscending);
    }

    // interval [start, end)
    void recursiveSort(int start, int end, int delay, boolean sortAscending) {
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
                controller.moveNumber(rightIndex, leftIndex);
                rightIndex++;
                leftIndex++;

                sleep(delay);
            }
            else {
                leftIndex++;
            }
        }
    }
}
