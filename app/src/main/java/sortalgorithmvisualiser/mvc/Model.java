package sortalgorithmvisualiser.mvc;

import java.util.Random;

public class Model {
    private int[] nums;
    private int maxValueAtCreation;

    private int accesses;
    private int assignments;

    public Model() {
        resetStats();
	}

    public int[] getList() {
        return nums.clone();
    }

    public int getValueAt(int index) {
        accesses++;
        return nums[index];
    }

    public int getArrayLength() {
        return nums.length;
    }

    public int getMaxValueAtCreation() {
        return maxValueAtCreation;
    }

    public void generateList(int length) {
        nums = new int[length];

        for (int i = 0; i < length; i++) {
            nums[i] = i + 1;
        }

        maxValueAtCreation = length;
        shuffle();
    }

    public void swapIndexes(int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
        accesses += 2;
        assignments += 2;
    }

    public void moveNumber(int currentIndex, int newIndex) {
		if (currentIndex == newIndex) {
			return;
		}

        int n = nums[currentIndex];
        accesses++;
		if (newIndex > currentIndex) {
			for (int i = currentIndex; i < newIndex; i++) {
				nums[i] = nums[i + 1];
                accesses++;
                assignments++;
			}
		}
		else if (newIndex < currentIndex) {
			for (int i = currentIndex; i > newIndex; i--) {
				nums[i] = nums[i - 1];
                accesses++;
                assignments++;
			}
		}
        nums[newIndex] = n;
        assignments++;
    }

    public void removeValueAt(int index) {
        if (index >= nums.length || index < 0) {
            throw new IllegalArgumentException();
        }

        int currentIndex = 0;
        int numCount = nums.length - 1;
        int[] newNumList = new int[numCount];
        for (int i = 0; i < numCount; i++) {
            if (i == index) {
                currentIndex++;
            }
            newNumList[i] = nums[currentIndex];
            currentIndex++;
        }

        nums = newNumList;
    }

    public void resetStats() {
        accesses = 0;
        assignments = 0;
    }

    public void shuffle() {
        shuffleRange(0, nums.length);
    }

    public void shuffleRange(int start, int end) {
        Random random = new Random();
        for (int i = end - 1; i > start; i--)
        {
            int index = random.nextInt(start, i + 1);
            int temp = nums[index];
            nums[index] = nums[i];
            nums[i] = temp;
        }
    }

    public int getAccessesCount() {
        return accesses;
    }

    public int getAssignmentsCount() {
        return assignments;
    }
}
