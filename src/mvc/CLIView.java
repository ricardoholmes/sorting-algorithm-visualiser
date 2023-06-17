package mvc;

import java.util.Scanner;

public class CLIView implements IView {
    Model model;

    @Override
    public void initialise(Model m, Controller c) {
        model = m;

        System.out.println("Choose a sorting algorithm from the list below:");
        for (int i = 0; i < c.getSorterCount(); i++) {
            System.out.println(i + ". " + c.getNameOfSorterAt(i));
        }
        System.out.print("Choice: ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        // no need to check if it's negative because it won't match the regex if it is
        while (!choice.matches("^[0-9]+$") || Integer.parseInt(choice) > c.getSorterCount()) {
            System.out.print("Invalid, choose again: ");
            scanner = new Scanner(System.in);
            choice = scanner.nextLine();
        }
        System.out.println();

        c.selectSorter(Integer.parseInt(choice));

        System.out.print("Delay between numbers moving position (milliseconds): ");
        choice = scanner.nextLine();
        while (!choice.matches("^[0-9]+$")) {
            System.out.print("Invalid, choose again: ");
            choice = scanner.nextLine();
        }
        int delay = Integer.parseInt(choice);

        System.out.print("Sort in ascending order (y or n)? ");
        choice = scanner.nextLine();
        while (!choice.matches("^[yn]$")) {
            System.out.print("Invalid, choose again: ");
            choice = scanner.nextLine();
        }
        boolean sortAscending = (choice == "y");

        c.sort(delay, sortAscending);

        scanner.close();
    }

    @Override
    public void refreshView() {
        int[] nums = model.getList();
        System.out.print("\r");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + (i == nums.length - 1 ? "" : ", "));
        }

        // if (isSorted) {
        //     System.out.println();
        // }
    }
    
}
