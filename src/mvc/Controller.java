package mvc;

import sorters.BogoSort;
import sorters.BubbleSort;
import sorters.Sorter;

public class Controller {
	Model model;
	IView view;

	private Sorter[] sorters = {
		new BubbleSort(),
		new BogoSort()
	};

	private Sorter sorter;

	private int chosenSorterIndex = 0;

	private Thread sortThread;

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
		if (delay < 0) {
			throw new IllegalArgumentException();
		}

		// fail silently if it is still being sorted
		if (sortThread != null && sortThread.isAlive()) {
			return;
		}

		sorter = sorters[chosenSorterIndex];
		sorter.initialise(this, model);

		sortThread = new Thread(() -> sorter.sort(delay));
		sortThread.start();
	}

	public void stopSorting() {
		// if (sorter == null || sortThread == null || !sortThread.isAlive()) {
		if (sorter == null || sortThread == null || !sortThread.isAlive()) {
			return;
		}
		sorter.stop();
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
		boolean isSorted = true;
		int[] nums = model.getList();
		for (int i = 0; i < nums.length - 1; i++) {
			if (nums[i + 1] < nums[i]) {
				isSorted = false;
				break;
			}
		}
		return isSorted;
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
