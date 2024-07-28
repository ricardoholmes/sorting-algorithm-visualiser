package sortalgorithmvisualiser.sorters;

public class SelectionSort extends Sorter {
    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public void sort() {
        for (int amountDone = 0; amountDone < sizeOfArray; amountDone++) {
            // will be largest if sorted in descending order
            int smallest = controller.getNumAtIndex(amountDone);
            int smallestIndex = amountDone;
            for (int i = amountDone; i < sizeOfArray; i++) {
                if (shouldStop) {
                    return;
                }

                int num = controller.getNumAtIndex(i);

                if (inOrder(num, smallest)) {
                    smallest = num;
                    smallestIndex = i;
                }
            }

            controller.moveNumber(smallestIndex, amountDone);
        }
    }
}
