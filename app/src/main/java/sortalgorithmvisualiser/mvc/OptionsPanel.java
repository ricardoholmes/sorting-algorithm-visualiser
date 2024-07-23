package sortalgorithmvisualiser.mvc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class OptionsPanel extends JPanel {
    private enum ColorOption {
        Red,
        Yellow,
        Green,
        Blue,
        Pink,
        Magenta,
        Cyan,
        Gray,
        Black,
        White;

        public Color toColor() {
            switch (this) {
                case Black:
                    return Color.BLACK;
                case Blue:
                    return Color.BLUE;
                case Cyan:
                    return Color.CYAN;
                case Gray:
                    return Color.GRAY;
                case Green:
                    return Color.GREEN;
                case Magenta:
                    return Color.MAGENTA;
                case Pink:
                    return Color.PINK;
                case Red:
                    return Color.RED;
                case White:
                    return Color.WHITE;
                case Yellow:
                    return Color.YELLOW;
                default:
                    return null;
            }
        }
    }

    private Controller controller;
    private Model model;

    private JSpinner delaySpinner;
    private JSpinner barCountSpinner;
    private JSpinner barBorderWidthSpinner;

    private JCheckBox sortAscendingCheckBox;
    private static JCheckBox muteCheckBox;

    private static JSlider volumeSlider;

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
            double delay = (double)delaySpinner.getValue();
            boolean ascending = sortAscendingCheckBox.isSelected();
            c.sort(delay, ascending);
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

        SpinnerModel barCountSpinnerModel = new SpinnerNumberModel(10, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        barCountSpinner = new JSpinner(barCountSpinnerModel);

        JButton generateArrayButton = new JButton("Generate");
        generateArrayButton.addActionListener(e -> {
            generateList();
        });

        SpinnerModel delaySpinnerModel = new SpinnerNumberModel(5.0, 0, Integer.MAX_VALUE, 1);
        delaySpinner = new JSpinner(delaySpinnerModel);

        JCheckBox borderActiveCheckBox = new JCheckBox("Border", true);
        borderActiveCheckBox.addActionListener(e -> {
            v.setBorderActive(borderActiveCheckBox.isSelected());
        });

        sortAscendingCheckBox = new JCheckBox("Sort Ascending", true);

        muteCheckBox = new JCheckBox("Mute", false);
        muteCheckBox.addActionListener(e -> {
            Sound.muted = muteCheckBox.isSelected();
        });

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);

        JComboBox<ColorOption> barColorDropDown = new JComboBox<>(ColorOption.values());
        barColorDropDown.setSelectedItem(ColorOption.Black);
        barColorDropDown.addItemListener(e -> {
            BarPanel.barColor = ((ColorOption)e.getItem()).toColor();
            BarPanel.refresh();
        });

        JComboBox<ColorOption> barComparingColorDropDown = new JComboBox<>(ColorOption.values());
        barComparingColorDropDown.setSelectedItem(ColorOption.Red);
        barComparingColorDropDown.addItemListener(e -> {
            BarPanel.barComparingColor = ((ColorOption)e.getItem()).toColor();
            BarPanel.refresh();
        });

        JComboBox<ColorOption> barDoneColorDropDown = new JComboBox<>(ColorOption.values());
        barDoneColorDropDown.setSelectedItem(ColorOption.Green);
        barDoneColorDropDown.addItemListener(e -> {
            BarPanel.barDoneColor = ((ColorOption)e.getItem()).toColor();
            BarPanel.refresh();
        });

        JComboBox<ColorOption> barBorderColorDropDown = new JComboBox<>(ColorOption.values());
        barBorderColorDropDown.setSelectedItem(ColorOption.White);
        barBorderColorDropDown.addItemListener(e -> {
            BarPanel.barBorderColor = ((ColorOption)e.getItem()).toColor();
            BarPanel.refresh();
        });

        JComboBox<ColorOption> barBackgroundColorDropDown = new JComboBox<>(ColorOption.values());
        barBackgroundColorDropDown.setSelectedItem(ColorOption.White);
        barBackgroundColorDropDown.addItemListener(e -> {
            Color color = ((ColorOption)e.getItem()).toColor();
            BarPanel.barBackgroundColor = color;
            BarPanel.refresh();
        });
        
        JComboBox<Oscillator.Wave> soundWaveDropDown = new JComboBox<>(Oscillator.Wave.values());
        soundWaveDropDown.setSelectedItem(ColorOption.Black);
        soundWaveDropDown.addItemListener(e -> {
            Oscillator.wave = (Oscillator.Wave)(e.getItem());
        });

        SpinnerModel barBorderWidthSpinnerModel = new SpinnerNumberModel(
            2,
            0,
            Integer.MAX_VALUE,
            1
        );
        barBorderWidthSpinner = new JSpinner(barBorderWidthSpinnerModel);
        barBorderWidthSpinner.addChangeListener(e -> {
            BarPanel.barBorderWidth = (int)barBorderWidthSpinner.getValue();
            BarPanel.refresh();
        });

        JCheckBox mergeBordersCheckBox = new JCheckBox("Merge Borders", false);
        mergeBordersCheckBox.addActionListener(e -> {
            BarPanel.mergeBorders = mergeBordersCheckBox.isSelected();
            BarPanel.refresh();
        });

        SpinnerModel marginSizeSpinnerModel = new SpinnerNumberModel(
            10,
            0,
            Integer.MAX_VALUE,
            1
        );
        JSpinner marginSizeSpinner = new JSpinner(marginSizeSpinnerModel);
        marginSizeSpinner.addChangeListener(e -> {
            BarPanel.marginSize = (int)marginSizeSpinner.getValue();
            BarPanel.refresh();
        });

        setBackground(Color.GRAY);
        
        // Select sorter
        addComponents(sorterDropDown);
        
        // select number of bars
        addComponents("Number of bars:", barCountSpinner);

        // generate array
        addComponents(generateArrayButton);

        // choose main delay
        addComponents("Delay (ms):", delaySpinner);

        // set volume (with slider)
        addComponents("Volume:", volumeSlider);

        // checkboxes
        addComponents(borderActiveCheckBox, sortAscendingCheckBox, muteCheckBox);

        // sort + stop
        addComponents(sortButton, stopButton, shuffleButton);

        // select colors
        addComponents("Bar color:", barColorDropDown);
        addComponents("Bar comparing color:", barComparingColorDropDown);
        addComponents("Bar done color:", barDoneColorDropDown);
        addComponents("Bar border color:", barBorderColorDropDown);
        addComponents("Bar background color:", barBackgroundColorDropDown);
        
        // sound settings
        addComponents("Sound wave:", soundWaveDropDown);

        // more customisation settings
        addComponents("Bar border width:", barBorderWidthSpinner);
        addComponents(mergeBordersCheckBox);
        addComponents("Margin size:", marginSizeSpinner);
    }

    private void addComponents(String labelText, Component component) {
        JLabel label = new JLabel(labelText);
        addComponents(label, component);
    }

    private void addComponents(Component... components) {
        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        for (Component c : components) {
            container.add(c);
        }
        container.setMaximumSize(container.getPreferredSize());
        add(container);
    }


    public static double getVolume() {
        return volumeSlider.getValue() / 100.0;
    }

    public void setMaximums(int barCount, int maxBorderWidth) {
        maxBars = barCount;

        int borderWidth = (int)barBorderWidthSpinner.getValue();
        if (borderWidth > maxBorderWidth) {
            barBorderWidthSpinner.setValue(maxBorderWidth);
        }

        if (model.getArrayLength() > maxBars) {
            barCountSpinner.setValue(maxBars);
            generateList();
        }
    }

    private void generateList() {
        int count = (int)barCountSpinner.getValue();
        if (count > maxBars) {
            count = maxBars;
        }
        else if (count < 1) {
            count = 1;
        }

        barCountSpinner.setValue(count);
        controller.generateList(count);
    }
}
