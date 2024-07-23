package sortalgorithmvisualiser.mvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.sampled.LineUnavailableException;

import sortalgorithmvisualiser.sorters.*;

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
		new HeapSort(),
		new BogoSort(),
		new StalinSort(),
		new BogobogoSort()
	};

	private Sorter sorter;

	private int chosenSorterIndex = 0;

	public Thread sortThread;

	public static double currentDelay;

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

		Controller.currentDelay = delay;

		BarPanel.resetBars();

		sorter = sorters[chosenSorterIndex];
		sorter.initialise(this, model.getArrayLength(), sortAscending, delay);

		try {
			Sound.initialise();
		} catch (LineUnavailableException e) {}

		sortThread = new Thread(() -> {
			sorter.sort();
			if (isSorted(sortAscending)) {
				view.doneSorting();
			} else {
				Sound.stopSound();
			}
		});
		sortThread.start();
	}

	public void stopSorting() {
		if (sorter == null || sortThread == null || !sortThread.isAlive()) {
			return;
		}

		// Sound.stopSound();
		Sound.stopSound();
		sorter.stop();
		BarPanel.stopDoneAnimation();
	}

	public void generateList(int length) {
		stopSorting();
		model.generateList(length);
		BarPanel.resetBars();
		view.refreshView();
	}

	public void shuffle() {
		model.shuffle();
		BarPanel.resetBars();
		view.refreshView();
	}

	public void shuffleRange(int start, int end) {
		model.shuffleRange(start, end);
		BarPanel.resetBars();
		view.refreshView();
	}

	public int getNumAtIndex(int index) {
		return model.getList()[index];
	}

	// the variable originally in position `i` will make the sound
	public void swapIndexes(int i, int j) {
		int[] nums = model.getList();
		int temp = nums[j];
		nums[j] = nums[i];
		nums[i] = temp;
		
		model.updateList(nums);
		view.refreshView();
	}

    public void removeIndex(int index) {
		model.removeValueAt(index);
		view.refreshView();
    }

	public void moveNumber(int currentIndex, int newIndex) {
		if (currentIndex == newIndex) {
			return;
		}
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
			if (ascending ^ (nums[i + 1] > nums[i])) {
				isSorted = false;
				break;
			}
		}
		return isSorted;
	}

	// only plays in GUI mode
	// will fail silently when called for CLI
	public void playSoundForValue(int value) {
		if (view.getClass() != GUIView.class || Sound.muted) {
			return;
		}

		double normalisedValue = value / (double)model.getMaxValueAtCreation();

		try {
			Sound.playCorrespondingSound(normalisedValue, currentDelay);
		} catch (LineUnavailableException e) { }
	}

    public void setComparing(int a, int b) {
		if (view.getClass() != GUIView.class) {
			return;
		}

		playSoundForValue(a);
		playSoundForValue(b);

		List<Integer> nums = Arrays.stream(model.getList())
									.boxed()
									.collect(Collectors.toList());

		int index1 = nums.indexOf(a);
		int index2 = nums.indexOf(b);

		BarPanel.comparingBars(index1, index2);
    }
}
