package mvc;

public class Controller {
	Model model;
	IView view;

    /*
	 * Initialise the controller
     * @param view The view to use
	 */
	public Controller(Model m, IView v) {
		view = v;
		model = m;
	}

	// /*
	//  * Start the program
	//  */
	// void startup() {
	// }

	public int getNumAtIndex(int index) {
		return model.getList()[index];
	}

	public void swapIndexes(int i, int j) {
		int[] nums = model.getList();
		int temp = nums[j];
		nums[j] = nums[i];
		nums[i] = temp;
		model.updateList(nums);

		update();
	}

	void update() {
		view.refreshView();
	}
}
