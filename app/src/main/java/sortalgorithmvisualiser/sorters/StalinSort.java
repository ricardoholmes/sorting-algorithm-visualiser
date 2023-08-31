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
        while (!shouldStop && i < model.getArrayLength()) {
            if (!inOrder(model.getValueAt(i-1), model.getValueAt(i), sortAscending)) {
                controller.playSoundForIndex(i, (int)delay);
                controller.removeIndex(i);
                sleep(delay);
            }
            else {
                i++;
            }
        }
    }
}
