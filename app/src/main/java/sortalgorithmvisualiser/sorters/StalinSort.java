package sortalgorithmvisualiser.sorters;

public class StalinSort extends Sorter {
    @Override
    public String getName() {
        return "Stalin Sort";
    }

    @Override
    public void sort(double delay, boolean sortAscending) {
        shouldStop = false;
        int i = 1;
        while (!shouldStop && i < sizeOfArray) {
            if (!inOrder(controller.getNumAtIndex(i-1), controller.getNumAtIndex(i), sortAscending)) {
                controller.playSoundForIndex(i, (int)delay);
                controller.removeIndex(i);
                sizeOfArray--;
                sleep(delay);
            }
            else {
                i++;
            }
        }
    }
}
