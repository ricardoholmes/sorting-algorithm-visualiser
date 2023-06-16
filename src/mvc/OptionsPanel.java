package mvc;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel {
    public OptionsPanel(Controller c) {
        String[] sortingAlgorithmNames = c.getSorterNames();
        JComboBox<String> sorterDropDown = new JComboBox<>(sortingAlgorithmNames);
        sorterDropDown.addItemListener(e -> {
            c.selectSorter(sorterDropDown.getSelectedIndex());
        });

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            c.sort(10);
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            c.stopSorting();
        });

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            c.shuffle();
        });

        add(sorterDropDown);
        add(sortButton);
        add(shuffleButton);
        add(stopButton);
    }
}
