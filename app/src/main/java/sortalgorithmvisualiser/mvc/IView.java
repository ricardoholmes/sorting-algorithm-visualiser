package sortalgorithmvisualiser.mvc;

public interface IView {
	/*
	 * Initialise the view
	 */
	void initialise(Model m, Controller c);
	
	/*
	 * Refresh the display
	 */
	void refreshView();

	void doneSorting();
}
