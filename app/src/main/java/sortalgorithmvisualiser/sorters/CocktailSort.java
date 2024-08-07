package sortalgorithmvisualiser.sorters;

public class CocktailSort extends Sorter {
    @Override
    public String getName() {
        return "Cocktail Shaker Sort";
    }

    @Override
    public void sort() {
        boolean sorted = false;
        while (!sorted) {
            // sort forwards
            sorted = true;
            for (int i = 0; i < sizeOfArray - 1; i++) {
                if (inOrder(controller.getNumAtIndex(i+1), controller.getNumAtIndex(i))) {
                    controller.swapIndexes(i, i + 1);
                    sorted = false;
                }
            }

            if (sorted) {
                break;
            }

            // sort backwards
            sorted = true;
            for (int i = sizeOfArray - 1; i > 0; i--) {
                if (inOrder(controller.getNumAtIndex(i), controller.getNumAtIndex(i-1))) {
                    controller.swapIndexes(i - 1, i);
                    sorted = false;
                }
            }
        }
    }
}
