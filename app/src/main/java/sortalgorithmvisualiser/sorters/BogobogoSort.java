package sortalgorithmvisualiser.sorters;

public class BogobogoSort extends Sorter {
    @Override
    public String getName() {
        return "Bogobogo Sort";
    }

    @Override
    public void sort(double delay, boolean sortAscending) {
        shouldStop = false;
        for (int i = 1; i < sizeOfArray; i++) {
            if (shouldStop) {
                return;
            }

            int prev = controller.getNumAtIndex(i-1);
            int n = controller.getNumAtIndex(i);

            if (!inOrder(prev, n, sortAscending)) {
                controller.shuffleRange(0, i+1);

                controller.playSoundForIndex(i-1, (int)delay);
                controller.playSoundForIndex(i, (int)delay);

                sleep(delay);

                i = 0; // loop will restart from 1 (since i++)
            }
        }
    }
}
