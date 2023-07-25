package sortalgorithmvisualiser.mvc;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class OptionsPanel extends JPanel {
    Controller controller;
    Model model;

    JSpinner delaySpinner;
    JSpinner numCountSpinner;

    JCheckBox sortAscendingCheckBox;
    static JCheckBox muteCheckBox;

    int maxBars;

    public OptionsPanel(Controller c, GUIView v, Model m) {
        controller = c;
        model = m;

        String[] sortingAlgorithmNames = c.getSorterNames();
        JComboBox<String> sorterDropDown = new JComboBox<>(sortingAlgorithmNames);
        sorterDropDown.addItemListener(e -> {
            c.selectSorter(sorterDropDown.getSelectedIndex());
        });

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            c.sort((double)delaySpinner.getValue(), sortAscendingCheckBox.isSelected());
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            c.stopSorting();
        });

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            if (c.sortThread == null || !c.sortThread.isAlive()) {
                c.shuffle();
            }
        });

        SpinnerModel numCountSpinnerModel = new SpinnerNumberModel(10, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        numCountSpinner = new JSpinner(numCountSpinnerModel);

        JButton generateArrayButton = new JButton("Generate");
        generateArrayButton.addActionListener(e -> {
            generateList();
        });

        SpinnerModel delaySpinnerModel = new SpinnerNumberModel(10.0, 0, Integer.MAX_VALUE, 1);
        delaySpinner = new JSpinner(delaySpinnerModel);

        JCheckBox borderActiveCheckBox = new JCheckBox("Border", true);
        borderActiveCheckBox.addActionListener(e -> {
            v.setBorderActive(borderActiveCheckBox.isSelected());
        });

        sortAscendingCheckBox = new JCheckBox("Sort Ascending", true);

        muteCheckBox = new JCheckBox("Mute", false);

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.GRAY);
        
        // Select sorter
        add(sorterDropDown);
        
        // select number of bars
        add(new JLabel("Number of bars:"));
        add(numCountSpinner);

        // generate array
        add(generateArrayButton);

        // choose delay
        add(new JLabel("Delay (ms):"));
        add(delaySpinner);

        // checkboxes
        add(borderActiveCheckBox);
        add(sortAscendingCheckBox);
        add(muteCheckBox);

        // sort + stop
        add(sortButton);
        add(stopButton);
        
        // shuffle
        add(shuffleButton);
    }

    public void setMaximumBarCount(int count) {
        maxBars = count;

        if (model.getArrayLength() > maxBars) {
            numCountSpinner.setValue(maxBars);
            generateList();
        }

    }

    void generateList() {
        int count = (int)numCountSpinner.getValue();
        if (count > maxBars) {
            count = maxBars;
        }
        else if (count < 1) {
            count = 1;
        }

        numCountSpinner.setValue(count);
        controller.generateList(count);
    }

    public static boolean isMuted() {
        return muteCheckBox.isSelected();
    }
}
