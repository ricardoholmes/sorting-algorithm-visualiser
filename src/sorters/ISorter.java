package sorters;

import mvc.Controller;
import mvc.Model;

public interface ISorter {
    public String getName();

    public void initialise(Controller c, Model m);

    /*
     * Sorts the array, refreshing UI at every change
     * Will get array from IModel, which will be passed through the constructor
     */
    public void sort();
}
