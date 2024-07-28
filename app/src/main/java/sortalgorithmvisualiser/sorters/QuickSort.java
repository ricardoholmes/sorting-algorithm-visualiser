package sortalgorithmvisualiser.sorters;

public class QuickSort extends Sorter {
    @Override
    public String getName() {
        return "Quick Sort";
    }

    @Override
    public void sort() {
        recursiveSort(0, sizeOfArray);
    }

    // inclusive start and exclusive end, ie. in interval [start, end)
    void recursiveSort(int start, int end) {
        if (start == end) {
            return;
        }

        int pivotPos = start;
        int pivotVal = controller.getNumAtIndex(start);
        for (int i = start + 1; i < end; i++) {
            if (inOrder(controller.getNumAtIndex(i), pivotVal)) {
                controller.moveNumber(i, start);
                pivotPos++;
            }
        }
        recursiveSort(start, pivotPos);
        recursiveSort(pivotPos + 1, end);
    }
}
