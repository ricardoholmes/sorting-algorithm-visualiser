package sortalgorithmvisualiser.mvc;

import java.util.Random;

public class Model {
    private int[] nums;
    private static int maxValueAtCreation;

    public Model() {
	}

    public int[] getList() {
        return nums.clone();
    }

    public int getValueAt(int index) {
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

    public void updateList(int[] newList) {
        if (nums.length != newList.length) {
            throw new IllegalArgumentException();
        }

        nums = newList;
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
}
