package sortalgorithmvisualiser.sorters;

public class StalinSort extends Sorter {
    @Override
    public String getName() {
        return "Stalin Sort";
    }

    @Override
    public void sort() {
        int i = 1;
        while (i < sizeOfArray) {
            if (!inOrder(controller.getNumAtIndex(i-1), controller.getNumAtIndex(i))) {
                controller.removeIndex(i);
                sizeOfArray--;
            }
            else {
                i++;
            }
        }
    }
}
