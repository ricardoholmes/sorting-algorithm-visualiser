package mvc;

import sorters.BogoSort;
import sorters.BubbleSort;
import sorters.ISorter;

public class Controller {
	Model model;
	IView view;

	private ISorter[] sorters = {
		new BubbleSort(),
		new BogoSort()
	};

	private int chosenSorterIndex = 0;

    /*
	 * Initialise the controller
     * @param view The view to use
	 */
	public Controller(Model m, IView v) {
		view = v;
		model = m;
	}

	public String getNameOfSorterAt(int index) {
		return sorters[index].getName();
	}

	public String[] getSorterNames() {
		String[] names = new String[sorters.length];

		for (int i = 0; i < sorters.length; i++) {
			names[i] = getNameOfSorterAt(i);
		}

		return names;
	}

	public int getSorterCount() {
		return sorters.length;
	}

	public void selectSorter(int index) {
		if (index < 0 || index > sorters.length - 1) {
			throw new IllegalArgumentException();
		}

		chosenSorterIndex = index;
	}

	public void sort(int delay) {
		ISorter sorter = sorters[chosenSorterIndex];
		sorter.initialise(this, model);

		Thread thread = new Thread(() -> sorter.sort(delay));
		thread.start();
	}

	public void shuffle() {
		model.shuffle();
		view.refreshView();
	}

	public int getNumAtIndex(int index) {
		return model.getList()[index];
	}

	public void swapIndexes(int i, int j) {
		int[] nums = model.getList();
		int temp = nums[j];
		nums[j] = nums[i];
		nums[i] = temp;
		model.updateList(nums);

		view.refreshView();
		// update();
	}

	public boolean isSorted() {
		boolean sorted = true;
		int[] nums = model.getList();
		for (int i = 0; i < nums.length - 1; i++) {
			if (nums[i + 1] < nums[i]) {
				sorted = false;
				break;
			}
		}
		return sorted;
	}

	// void update() {
	// 	// int[] nums = model.getList();
	// 	// for (int i = 0; i < nums.length - 1; i++) {
	// 	// 	if (nums[i + 1] > nums[i]) {
	// 	// 		view.refreshView(false);
	// 	// 		return;
	// 	// 	}
	// 	// }
	// 	// view.refreshView(true);
	// 	view.refreshView();
	// }
}
