package sortalgorithmvisualiser.sorters;

public class MergeSort extends Sorter {
    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public void sort() {
        shouldStop = false;
        recursiveSort(0, sizeOfArray);
    }

    // interval [start, end)
    void recursiveSort(int start, int end) {
        if (start + 1 == end) {
            return;
        }

        int middle = start + ((end - start) / 2);

        recursiveSort(start, middle);
        recursiveSort(middle, end);

        int leftIndex = start;
        int rightIndex = middle;

        while (leftIndex < rightIndex && rightIndex < end) {
            if (shouldStop) {
                return;
            }

            int leftNum = controller.getNumAtIndex(leftIndex);
            int rightNum = controller.getNumAtIndex(rightIndex);
            if (inOrder(rightNum, leftNum)) {
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
