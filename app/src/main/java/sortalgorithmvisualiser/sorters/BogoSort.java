package sortalgorithmvisualiser.sorters;

public class BogoSort extends Sorter {
    @Override
    public String getName() {
        return "Bogo Sort";
    }

    @Override
    public void sort() {
        while (!isSorted()) {
            controller.shuffle();
        }
    }
}
