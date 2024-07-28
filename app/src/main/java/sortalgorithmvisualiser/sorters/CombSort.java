package sortalgorithmvisualiser.sorters;

public class CombSort extends Sorter {
    @Override
    public String getName() {
        return "Comb Sort";
    }

    @Override
    public void sort() {
        int gap = sizeOfArray;
        double shrink = 1.3;
        boolean sorted = false;
    
        while (!sorted) {
            gap = (int)(gap / shrink);
            if (gap <= 1) {
                gap = 1;
                sorted = true;
            }
            else if (gap == 9 || gap == 10) {
                gap = 11;
            }
    
            for (int i = 0; i + gap < sizeOfArray; i++) {
                if (!inOrderIndexes(i, i+gap)) {
                    controller.swapIndexes(i, i+gap);
                    sorted = false;
                }
            }
        }
    }
}
