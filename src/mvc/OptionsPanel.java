package mvc;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class OptionsPanel extends JPanel {
    JSpinner delaySpinner;
    JSpinner countSpinner;

    public OptionsPanel(Controller c) {
        String[] sortingAlgorithmNames = c.getSorterNames();
        JComboBox<String> sorterDropDown = new JComboBox<>(sortingAlgorithmNames);
        sorterDropDown.addItemListener(e -> {
            c.selectSorter(sorterDropDown.getSelectedIndex());
        });

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            c.sort((int)delaySpinner.getValue());
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            c.stopSorting();
        });

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            c.shuffle();
        });

        SpinnerModel delaySpinnerModel = new SpinnerNumberModel(10, 0, Integer.MAX_VALUE, 1);
        delaySpinner = new JSpinner(delaySpinnerModel);

        SpinnerModel countSpinnerModel = new SpinnerNumberModel(10, 1, Integer.MAX_VALUE, 1);
        countSpinner = new JSpinner(countSpinnerModel);

        JButton generateArrayButton = new JButton("Generate");
        generateArrayButton.addActionListener(e -> {
            c.generateList((int)countSpinner.getValue());
        });

        add(sorterDropDown);
        add(countSpinner);
        add(generateArrayButton);
        add(delaySpinner);
        add(sortButton);
        add(shuffleButton);
        add(stopButton);
    }

    public void setMaximumBarCount(int count) {
        int value = (int)countSpinner.getValue();
        if (value > count) {
            value = count;
        }

        SpinnerModel countSpinnerModel = new SpinnerNumberModel((int)countSpinner.getValue(), 1, count, 1);
        countSpinner.setModel(countSpinnerModel);

    }
}
