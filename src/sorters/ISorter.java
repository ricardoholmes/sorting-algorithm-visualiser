package sorters;

import mvc.Controller;
import mvc.Model;

public interface ISorter {
    public void initialise(Controller c, Model m);

    public String getName();
    
    /*
     * Sorts the array, refreshing UI at every change
     * Will get array from IModel, which will be passed through the constructor
     * @param delay The delay between numbers changing positions
     */
    public void sort(int delay);
}
