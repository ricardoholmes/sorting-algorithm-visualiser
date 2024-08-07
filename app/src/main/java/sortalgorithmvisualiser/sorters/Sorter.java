package sortalgorithmvisualiser.sorters;

import org.w3c.dom.events.EventException;

import sortalgorithmvisualiser.mvc.Controller;

public abstract class Sorter {
    protected Controller controller;
    private double delay;
    protected int sizeOfArray;
    private boolean sortAscending;
    
    private boolean shouldStop;
    
    private static int comparisons = 0;
    
    /**
     * @param c
     *      the controller
     * 
     * @param sizeOfArray
     *      the number of numbers in the list that is to be sorted
     * 
     * @param delay
     *      the length of time to sleep after moving a value, in milliseconds
     * 
     * @param sortAscending
     *      whether the list is being sorted in ascending order (true) or in
     *      descending order (false)
     */
    public final void initialise(Controller c, int sizeOfArray, boolean sortAscending, double delay) {
        controller = c;
        this.sizeOfArray = sizeOfArray;
        this.delay = delay;
        this.sortAscending = sortAscending;
        comparisons = 0;
        shouldStop = false;
    }

    /**
     * @return the name of the sorting algorithm
     */
    public abstract String getName();

    /**
     * @return The number of comparisons made during the sorting so far
     */
    public static final int getComparisonsCount() {
        return comparisons;
    }

    /**
     * Sorts the list
     */
    public abstract void sort();

    /**
     * @param a
     *      a value from the list
     * 
     * @param b
     *      a value from the list, different to {@code a}
     * 
     * @param sortAscending
     *      whether the list is being sorted in ascending order (true) or in
     *      descending order (false)
     * 
     * @return whether or not {@code a} should be followed by {@code b} if sorted in
     *          the order given by {@code sortAscending}
     */
    protected final boolean inOrder(int a, int b) {
        comparisons++;
        controller.setComparing(a, b);
        sleep();

        // sortAscending AND !(a > b)
        // OR
        // !sortAscending AND (a > b)
        // THEREFORE
        // sortAscending XOR a > b
        return sortAscending ^ (a > b);
    }

    /**
     * @param i
     *      the index of a value from the list
     * 
     * @param j
     *      the index of a value from the list, different to {@code i}
     * 
     * @param sortAscending
     *      whether the list is being sorted in ascending order (true) or in
     *      descending order (false)
     * 
     * @return
     *      whether or not the element in position {@code i} should be followed by the
     *      element in position {@code j} if sorted in
     *      the order given by {@code sortAscending}
     */
    protected final boolean inOrderIndexes(int i, int j) {
        int a = controller.getNumAtIndex(i);
        int b = controller.getNumAtIndex(j);
        return inOrder(a, b);
    }

    protected final boolean isSorted() {
        for (int i = 0; i < sizeOfArray - 1; i++) {
            int a = controller.getNumAtIndex(i);
            int b = controller.getNumAtIndex(i + 1);
            if (!inOrder(a, b)) {
                return false;
            }
        }
        return true;
    }

    private final void sleep() {
        long millis = (long)delay;
        int nanos = (int)((delay % 1) * 1_000_000);

        try {
            Thread.sleep(0, nanos);
        } catch (InterruptedException e) { }

        if (shouldStop) {
            throw new EventException((short)1, "Stop sorting"); // should get caught by the controller
        }
        for (int i = 0; i < millis; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) { }

            if (shouldStop) {
                throw new EventException((short)1, "Stop sorting"); // should get caught by the controller
            }
        }
    }

    public final void stop() {
        shouldStop = true;
    }
}
