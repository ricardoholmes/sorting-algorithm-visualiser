package sortalgorithmvisualiser.mvc;

import java.awt.Color;
import java.awt.Container;
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
    private Controller controller;
    private Model model;

    private JSpinner delaySpinner;
    private JSpinner endDelaySpinner;
    private JSpinner numCountSpinner;

    private JCheckBox sortAscendingCheckBox;
    private static JCheckBox muteCheckBox;

    private int maxBars;

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
            c.sort((double)delaySpinner.getValue(), sortAscendingCheckBox.isSelected(), (double)endDelaySpinner.getValue());
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            c.stopSorting();
        });

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            if (!c.sortThread.isAlive()) {
                c.shuffle();
            }
        });

        SpinnerModel numCountSpinnerModel = new SpinnerNumberModel(10, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        numCountSpinner = new JSpinner(numCountSpinnerModel);

        JButton generateArrayButton = new JButton("Generate");
        generateArrayButton.addActionListener(e -> {
            generateList();
        });

        SpinnerModel delaySpinnerModel = new SpinnerNumberModel(25.0, 0, Integer.MAX_VALUE, 1);
        delaySpinner = new JSpinner(delaySpinnerModel);

        SpinnerModel endDelaySpinnerModel = new SpinnerNumberModel(50.0, 0, Integer.MAX_VALUE, 1);
        endDelaySpinner = new JSpinner(endDelaySpinnerModel);

        JCheckBox borderActiveCheckBox = new JCheckBox("Border", true);
        borderActiveCheckBox.addActionListener(e -> {
            v.setBorderActive(borderActiveCheckBox.isSelected());
        });

        sortAscendingCheckBox = new JCheckBox("Sort Ascending", true);

        muteCheckBox = new JCheckBox("Mute", false);

        setBackground(Color.GRAY);
        
        // Select sorter
        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(sorterDropDown);
        container.setMaximumSize(container.getPreferredSize());
        add(container);
        
        // select number of bars
        container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel numberOfBarsLabel = new JLabel("Number of bars:");
        container.add(numberOfBarsLabel);
        container.add(numCountSpinner);
        container.setMaximumSize(container.getPreferredSize());
        add(container);

        // generate array
        container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(generateArrayButton);
        container.setMaximumSize(container.getPreferredSize());
        add(container);

        // choose delay
        container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel delayLabel = new JLabel("Delay (ms):");
        container.add(delayLabel);
        container.add(delaySpinner);
        container.setMaximumSize(container.getPreferredSize());
        add(container);

        // choose delay for end animation
        container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel endDelayLabel = new JLabel("End Delay (ms):");
        container.add(endDelayLabel);
        container.add(endDelaySpinner);
        container.setMaximumSize(container.getPreferredSize());
        add(container);

        // checkboxes
        container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(borderActiveCheckBox);
        container.add(sortAscendingCheckBox);
        container.add(muteCheckBox);
        container.setMaximumSize(container.getPreferredSize());
        add(container);

        // sort + stop
        container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(sortButton);
        container.add(stopButton);
        container.add(shuffleButton);
        container.setMaximumSize(container.getPreferredSize());
        add(container);
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
