package sortalgorithmvisualiser.mvc;

import java.util.Scanner;

public class CLIView implements IView {
    Model model;
    Controller controller;
    Scanner scanner;

    @Override
    public void initialise(Model m, Controller c) {
        model = m;
        controller = c;

        System.out.println("Choose a sorting algorithm from the list below:");
        for (int i = 0; i < c.getSorterCount(); i++) {
            System.out.println(i + ". " + c.getNameOfSorterAt(i));
        }
        System.out.print("Choice (enter your desired algorithm's corresponding number): ");

        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        String choice = scanner.nextLine();

        // no need to check if it's negative because it won't match the regex if it is
        while (!choice.matches("^[0-9]+$") || Integer.parseInt(choice) >= c.getSorterCount()) {
            System.out.print("Invalid, choose again (enter your desired algorithm's corresponding number): ");
            scanner = new Scanner(System.in);
            choice = scanner.nextLine();
        }

        c.selectSorter(Integer.parseInt(choice));

        System.out.print("Size of the list of numbers that is to be sorted: ");
        choice = scanner.nextLine();
        while (!choice.matches("^[0-9]+$") || Integer.parseInt(choice) < 1) {
            System.out.print("Invalid, choose again: ");
            choice = scanner.nextLine();
        }
        int size = Integer.parseInt(choice);

        System.out.print("Delay between numbers moving position (milliseconds): ");
        choice = scanner.nextLine();
        while (!choice.matches("^[0-9]+(\\.[0-9]+)?$")) {
            System.out.print("Invalid, choose again: ");
            choice = scanner.nextLine();
        }
        double delay = Double.parseDouble(choice);

        System.out.print("Sort in ascending order (y or n)? ");
        choice = scanner.nextLine();
        while (!choice.matches("^[yn]$")) {
            System.out.print("Invalid, choose again: ");
            choice = scanner.nextLine();
        }
        boolean sortAscending = (choice.compareTo("y") == 0);

        System.out.print("\033[2J\033[H\u001b[s"); // clear screen, go to start, and save cursor position
        model.generateList(size);
        c.sort(delay, sortAscending);
    }

    @Override
    public void refreshView() {
        int[] nums = model.getList();
        System.out.print("\u001b[u"); // reset cursor position to saved
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + (i == nums.length - 1 ? "" : ", "));
        }
    }

    @Override
    public void doneSorting() {
        System.out.print("\n\nRun again (y or n)? ");
        String choice = scanner.nextLine();
        while (!choice.matches("^[yn]$")) {
            System.out.print("Invalid, choose again: ");
            choice = scanner.nextLine();
        }

        if (choice.compareTo("n") == 0) {
            scanner.close();
            System.exit(0);
            return;
        }

        initialise(model, controller);
    }
}
