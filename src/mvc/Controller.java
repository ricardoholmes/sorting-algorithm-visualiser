package mvc;

import javax.sound.sampled.LineUnavailableException;

import sorters.*;

public class Controller {
	Model model;
	IView view;

	private Sorter[] sorters = {
		new BubbleSort(),
		new SelectionSort(),
		new InsertionSort(),
		new CocktailSort(),
		new QuickSort(),
		new MergeSort(),
		new BogoSort()
	};

	private Sorter sorter;

	private int chosenSorterIndex = 0;

	public Thread sortThread;

	private double currentDelay;

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

	public void sort(double delay, boolean sortAscending) {
		if (delay < 0) {
			throw new IllegalArgumentException();
		}

		// fail silently if it is still being sorted
		if (sortThread != null && sortThread.isAlive()) {
			return;
		}

		currentDelay = delay;

		BarPanel.resetBarColor();

		sorter = sorters[chosenSorterIndex];
		sorter.initialise(this, model);

		sortThread = new Thread(() -> {
			sorter.sort(delay, sortAscending);
			if (isSorted(sortAscending)) {
				view.doneSorting();
			}
		});
		sortThread.start();
	}

	public void stopSorting() {
		// if (sorter == null || sortThread == null || !sortThread.isAlive()) {
		if (sorter == null || sortThread == null || !sortThread.isAlive()) {
			return;
		}
		sorter.stop();
		BarPanel.stopDoneAnimation();
	}

	public void generateList(int length) {
		stopSorting();
		model.generateList(length);
		BarPanel.resetBarSample();
		BarPanel.resetBarColor();
		view.refreshView();
	}

	public void shuffle() {
		model.shuffle();
		BarPanel.resetBarColor();
		view.refreshView();
	}

	public int getNumAtIndex(int index) {
		return model.getList()[index];
	}

	// the variable originally in position `i` will make the sound
	public void swapIndexes(int i, int j) {
		playSoundForIndex(i, (int)currentDelay);

		int[] nums = model.getList();
		int temp = nums[j];
		nums[j] = nums[i];
		nums[i] = temp;
		
		model.updateList(nums);
		view.refreshView();
	}

	public void moveNumber(int currentIndex, int newIndex) {
		if (currentIndex == newIndex) {
			return;
		}

		playSoundForIndex(currentIndex, (int)currentDelay);

		int[] nums = model.getList();
		int currentNum = nums[currentIndex];

		if (newIndex > currentIndex) {
			for (int i = currentIndex; i < newIndex; i++) {
				nums[i] = nums[i + 1];
			}
		}
		else if (newIndex < currentIndex) {
			for (int i = currentIndex; i > newIndex; i--) {
				nums[i] = nums[i - 1];
			}
		}

		nums[newIndex] = currentNum;
		model.updateList(nums);
		view.refreshView();
	}

	public boolean isSorted(boolean ascending) {
		boolean isSorted = true;
		int[] nums = model.getList();
		for (int i = 0; i < nums.length - 1; i++) {
			// (ascending AND !(nums[i+1] > nums[i])) OR (!ascending AND (nums[i+1] > nums[i]))
			if (ascending ^ (nums[i + 1] > nums[i])) {
				isSorted = false;
				break;
			}
		}
		return isSorted;
	}

	// only works for GUI
	public void playSoundForIndex(int index, int millis) {
		if (millis == 0 || OptionsPanel.isMuted()) {
			return;
		}

		double normalisedValue = (getNumAtIndex(index) - 1) / (double)(model.getArrayLength() - 1);

		// took this from https://panthema.net/2013/sound-of-sorting/sound-of-sorting-0.6.5/src/SortSound.cpp.html
		int hz = 120 + (int)(1200 * (normalisedValue * normalisedValue));

		Thread soundThread = new Thread(() -> {
			try {
				Sound.playTone(hz, millis + 10);
			} catch (LineUnavailableException e) { }
		});
		soundThread.start();
	}
}
