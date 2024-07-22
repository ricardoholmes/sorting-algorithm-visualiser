package sortalgorithmvisualiser.sorters;

public class BogoSort extends Sorter {
    @Override
    public String getName() {
        return "Bogo Sort";
    }

    @Override
    public void sort() {
        shouldStop = false;
        while (!isSorted()) {
            if (shouldStop) {
                return;
            }

            controller.shuffle();
        }
    }
}
