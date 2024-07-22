package sortalgorithmvisualiser.sorters;

public class HeapSort extends Sorter {
    @Override
    public String getName() {
        return "Heap Sort";
    }

    @Override
    public void sort() {
        shouldStop = false;
        int start = sizeOfArray / 2;
        int end = sizeOfArray;
        while (!shouldStop && end > 1) {
            if (start > 0) {
                start--;
            }
            else {
                end--;
                sleep(delay);
                controller.swapIndexes(end, 0);
            }

            int root = start;
            while (!shouldStop && leftChild(root) < end) {
                int rootVal = controller.getNumAtIndex(root);

                int child = leftChild(root);
                int childVal = controller.getNumAtIndex(child);

                if (child+1 < end) {
                    int rightVal = controller.getNumAtIndex(child + 1);
                    if (inOrder(childVal, rightVal, sortAscending)) {
                        child++;
                        childVal = rightVal;
                    }
                }

                if (inOrder(rootVal, childVal, sortAscending)) {
                    sleep(delay);
                    controller.swapIndexes(root, child);
                    root = child;
                }
                else {
                    break;
                }
            }
        }
    }

    private int leftChild(int index) {
        return (index * 2) + 1;
    }
}
