package sortalgorithmvisualiser.sorters;

public class CocktailSort extends Sorter {
    @Override
    public String getName() {
        return "Cocktail Shaker Sort";
    }

    @Override
    public void sort(double delay, boolean sortAscending) {
        shouldStop = false;
        boolean sorted = false;
        while (!sorted) {
            // sort forwards
            sorted = true;
            for (int i = 0; i < model.getArrayLength() - 1; i++) {
                if (shouldStop) {
                    return;
                }

                if (inOrder(controller.getNumAtIndex(i+1), controller.getNumAtIndex(i), sortAscending)) {
                    controller.swapIndexes(i, i + 1);
                    sorted = false;

                    sleep(delay);
                }
            }

            if (sorted) {
                break;
            }

            // sort backwards
            sorted = true;
            for (int i = model.getArrayLength() - 1; i > 0; i--) {
                if (shouldStop) {
                    return;
                }

                if (inOrder(controller.getNumAtIndex(i), controller.getNumAtIndex(i-1), sortAscending)) {
                    controller.swapIndexes(i - 1, i);
                    sorted = false;

                    sleep(delay);
                }
            }
        }
    }
}
