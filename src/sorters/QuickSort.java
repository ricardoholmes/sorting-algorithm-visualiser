package sorters;

public class QuickSort extends Sorter {
    @Override
    public String getName() {
        return "Quick Sort";
    }

    @Override
    public void sort(double delay, boolean sortAscending) {
        shouldStop = false;
        recursiveSort(delay, sortAscending, 0, model.getArrayLength());
    }

    // inclusive start and exclusive end, ie. in interval [start, end)
    void recursiveSort(double delay, boolean sortAscending, int start, int end) {
        if (start == end) {
            return;
        }

        int pivotPos = start;
        int pivotVal = controller.getNumAtIndex(start);
        for (int i = start + 1; i < end; i++) {
            if (shouldStop) {
                return;
            }

            if (inOrder(controller.getNumAtIndex(i), pivotVal, sortAscending)) {
                controller.moveNumber(i, start);
                pivotPos++;

                sleep(delay);
            }
        }
        recursiveSort(delay, sortAscending, start, pivotPos);
        recursiveSort(delay, sortAscending, pivotPos + 1, end);
    }
}
