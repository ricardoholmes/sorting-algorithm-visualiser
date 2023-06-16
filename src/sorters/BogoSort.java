package sorters;

public class BogoSort extends Sorter {
    @Override
    public String getName() {
        return "Bogo Sort";
    }

    @Override
    public void sort(int delay) {
        shouldStop = false;
        while (!controller.isSorted()) {
            if (shouldStop) {
                return;
            }

            controller.shuffle();

            sleep(delay);
        }
    }
}
