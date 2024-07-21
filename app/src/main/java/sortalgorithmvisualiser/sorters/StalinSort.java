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
                sleep(delay);
                controller.playSoundForIndex(i);
                controller.removeIndex(i);
                sizeOfArray--;
            }
            else {
                i++;
            }
        }
    }
}
