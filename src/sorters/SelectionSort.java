package sorters;

public class SelectionSort extends Sorter {
    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public void sort(double delay, boolean sortAscending) {
        shouldStop = false;
        for (int amountDone = 0; amountDone < model.getArrayLength(); amountDone++) {
            // will be largest if sorted in descending order
            int smallest = controller.getNumAtIndex(amountDone);
            int smallestIndex = amountDone;
            for (int i = amountDone; i < model.getArrayLength(); i++) {
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

            controller.moveNumber(smallestIndex, amountDone);
            sleep(delay);
        }
    }
}
