package sortalgorithmvisualiser.sorters;

public class BitonicSort extends Sorter {
    @Override
    public String getName() {
        return "Bitonic Sort";
    }

    @Override
    public void sort() {
        powerOfTwoSplit(0, sizeOfArray);
    }

    private void powerOfTwoSplit(int start, int end) {
        int size = end - start; // exclusive end, inclusive start

        // if not a power of two
        if ((size & (size - 1)) != 0) {
            int midpoint = start + Integer.highestOneBit(size);
            powerOfTwoSplit(start, midpoint);
            powerOfTwoSplit(midpoint, end);
            merge(start, midpoint, end);
            return;
        }

        bitonicSort(start, size, true);
    }

    private void merge(int start, int midpoint, int end) {
        // standard mergesort merge
        while (start < midpoint && midpoint < end) {
            if (inOrderIndexes(start, midpoint)) {
                start++;
            }
            else {
                controller.moveNumber(midpoint, start);
                start++;
                midpoint++;
            }

            if (shouldStop) {
                return;
            }
        }
    }

    private void bitonicSort(int start, int count, boolean direction) {
        if (count <= 1) {
            return;
        }

        int k = count / 2;

        bitonicSort(start, k, true);
        bitonicSort(start+k, k, false);
        bitonicMerge(start, count, direction);
    }

    private void bitonicMerge(int start, int count, boolean direction) {
        if (count <= 1) {
            return;
        }

        int k = count / 2;
        for (int i = start; i < start+k; i++) {
            if (inOrderIndexes(i+k, i) == direction) {
                controller.swapIndexes(i, i+k);
            }

            if (shouldStop) {
                return;
            }
        }

        bitonicMerge(start, k, direction);
        bitonicMerge(start+k, k, direction);
    }
}
