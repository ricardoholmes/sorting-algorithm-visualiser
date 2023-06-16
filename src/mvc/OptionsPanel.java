package mvc;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class OptionsPanel extends JPanel {
    Controller controller;
    Model model;

    JSpinner delaySpinner;
    JSpinner countSpinner;

    int maxBars;

    public OptionsPanel(Controller c, Model m) {
        controller = c;
        model = m;

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

        SpinnerModel countSpinnerModel = new SpinnerNumberModel(10, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        countSpinner = new JSpinner(countSpinnerModel);

        JButton generateArrayButton = new JButton("Generate");
        generateArrayButton.addActionListener(e -> {
            c.generateList((int)countSpinner.getValue());
        });

        SpinnerModel delaySpinnerModel = new SpinnerNumberModel(10, 0, Integer.MAX_VALUE, 1);
        delaySpinner = new JSpinner(delaySpinnerModel);

        add(sorterDropDown);
        add(countSpinner);
        add(generateArrayButton);
        add(delaySpinner);
        add(sortButton);
        add(shuffleButton);
        add(stopButton);
    }

    public void setMaximumBarCount(int count) {
        maxBars = count;

        if (model.getArrayLength() > maxBars) {
            countSpinner.setValue(maxBars);
            generateList();
        }

    }

    void generateList() {
        int count = (int)countSpinner.getValue();
        if (count > maxBars) {
            count = maxBars;
        }
        else if (count < 1) {
            count = 1;
        }

        countSpinner.setValue(count);
        controller.generateList(count);
    }
}
