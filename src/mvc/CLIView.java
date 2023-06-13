package mvc;

import java.util.Scanner;

import sorters.*;

public class CLIView implements IView {
    Model model;

    @Override
    public void initialise(Model m, Controller c) {
        model = m;

        ISorter[] sorters = { new BubbleSort(), new BubbleSort(), new BubbleSort(), new BubbleSort(), new BubbleSort() };

        System.out.println("Choose a sorting algorithm from the list below:");
        for (int i = 0; i < sorters.length; i++) {
            System.out.println(i + ". " + sorters[i].getName());
        }
        System.out.print("Choice: ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        
        // no need to check if it's negative because it won't match the regex if it is
        while (!choice.matches("^[0-9]+$") && Integer.parseInt(choice) < sorters.length) {
            System.out.print("Invalid, choose again: ");
            scanner = new Scanner(System.in);
            choice = scanner.nextLine();
        }
        System.out.println();

        int n = Integer.parseInt(choice);
        ISorter sorter = sorters[n];
        sorter.initialise(c, m);
        sorter.sort();

        scanner.close();
    }

    @Override
    public void refreshView() {
        int[] nums = model.getList();
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + (i == nums.length - 1 ? "" : ", "));
        }
        System.out.println();
    }
    
}
