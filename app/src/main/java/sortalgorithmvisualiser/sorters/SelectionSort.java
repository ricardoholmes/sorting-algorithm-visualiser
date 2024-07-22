package sortalgorithmvisualiser.sorters;

public class SelectionSort extends Sorter {
    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public void sort() {
        shouldStop = false;
        for (int amountDone = 0; amountDone < sizeOfArray; amountDone++) {
            // will be largest if sorted in descending order
            int smallest = controller.getNumAtIndex(amountDone);
            int smallestIndex = amountDone;
            for (int i = amountDone; i < sizeOfArray; i++) {
                if (shouldStop) {
                    return;
                }

                int num = controller.getNumAtIndex(i);

                // in case of ascending, if num < smallest
                if (inOrder(num, smallest, sortAscending)) {
                    smallest = num;
                    smallestIndex = i;
                }
            }

            sleep(delay);
            controller.moveNumber(smallestIndex, amountDone);
        }
    }
}
