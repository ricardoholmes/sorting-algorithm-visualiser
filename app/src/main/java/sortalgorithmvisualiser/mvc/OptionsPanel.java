package sortalgorithmvisualiser.mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
            return switch (this) {
                case Black -> Color.BLACK;
                case Blue -> Color.BLUE;
                case Cyan -> Color.CYAN;
                case Gray -> Color.GRAY;
                case Green -> Color.GREEN;
                case Magenta -> Color.MAGENTA;
                case Pink -> Color.PINK;
                case Red -> Color.RED;
                case White -> Color.WHITE;
                case Yellow -> Color.YELLOW;
                default -> null;
            };
        }
    }

    private Model model;
    private GUIView view;
    private Controller controller;

    private JScrollPane mainScrollPane;

    private JPanel homePanel;
    private JPanel soundPanel;
    private JPanel graphicsPanel;
    private JPanel statsPanel;

    private JSpinner delaySpinner;
    private JSpinner barCountSpinner;
    private JSpinner barBorderWidthSpinner;

    private JCheckBox sortAscendingCheckBox;
    private static JCheckBox muteCheckBox;

    private static JSlider volumeSlider;

    public JButton popOutButton;

    private int maxBars;

    public OptionsPanel(Controller c, Model m, GUIView v) {
        controller = c;
        model = m;
        view = v;

        popOutButton = new JButton("Pop Out");
        popOutButton.addActionListener(e -> {
            if (v.optionsPoppedOut) {
                v.popInOptions();
            } else {
                v.popOutOptions();
            }
        });

        JButton homeButton = new JButton("Home");
        homeButton.setMargin(new Insets(0, 0, 0, 0));
        homeButton.addActionListener(e -> {
            mainScrollPane.setViewportView(homePanel);
        });

        JButton soundButton = new JButton("Sound");
        soundButton.setMargin(new Insets(0, 0, 0, 0));
        soundButton.addActionListener(e -> {
            mainScrollPane.setViewportView(soundPanel);
        });

        JButton graphicsButton = new JButton("Graphics");
        graphicsButton.setMargin(new Insets(0, 0, 0, 0));
        graphicsButton.addActionListener(e -> {
            mainScrollPane.setViewportView(graphicsPanel);
        });

        JButton statsButton = new JButton("Stats");
        statsButton.setMargin(new Insets(0, 0, 0, 0));
        statsButton.addActionListener(e -> {
            mainScrollPane.setViewportView(statsPanel);
        });
        
        setLayout(new BorderLayout());

        // navigation bar
        JPanel navBar = new JPanel();
        navBar.setLayout(new GridLayout(1, 3));
        navBar.add(homeButton);
        navBar.add(graphicsButton);
        navBar.add(soundButton);
        navBar.add(statsButton);
        int navBarWidth = getPreferredSize().width;
        int navBarHeight = homeButton.getPreferredSize().width;
        navBar.setPreferredSize(new Dimension(navBarWidth, navBarHeight));
        add(navBar, BorderLayout.PAGE_START);

        // menus
        homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        initialiseHomePanel();

        soundPanel = new JPanel();
        soundPanel.setLayout(new BoxLayout(soundPanel, BoxLayout.Y_AXIS));
        initialiseSoundPanel();

        graphicsPanel = new JPanel();
        graphicsPanel.setLayout(new BoxLayout(graphicsPanel, BoxLayout.Y_AXIS));
        initialiseGraphicsPanel();

        statsPanel = new StatsPanel(model, view);

        mainScrollPane = new JScrollPane(homePanel);
        add(mainScrollPane, BorderLayout.CENTER);

        // pop in/out
        popOutButton.setPreferredSize(new Dimension(navBarWidth, navBarHeight));
        add(popOutButton, BorderLayout.PAGE_END);

        mainScrollPane.setBackground(Color.GRAY);
    }

    private void initialiseHomePanel() {
        String[] sortingAlgorithmNames = controller.getSorterNames();
        JComboBox<String> sorterDropDown = new JComboBox<>(sortingAlgorithmNames);
        sorterDropDown.addItemListener(e -> {
            controller.selectSorter(sorterDropDown.getSelectedIndex());
        });

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            double delay = (double)delaySpinner.getValue();
            boolean ascending = sortAscendingCheckBox.isSelected();
            controller.sort(delay, ascending);
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            controller.stopSorting();
        });

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            if (controller.sortThread == null || !controller.sortThread.isAlive()) {
                controller.shuffle();
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

        sortAscendingCheckBox = new JCheckBox("Sort Ascending", true);

        muteCheckBox = new JCheckBox("Mute", false);
        muteCheckBox.addActionListener(e -> {
            Sound.muted = muteCheckBox.isSelected();
        });

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);

        // Select sorter
        addComponents(homePanel, sorterDropDown);
        
        // select number of bars
        addComponents(homePanel, "Number of bars:", barCountSpinner);

        // generate array
        addComponents(homePanel, generateArrayButton);

        // choose main delay
        addComponents(homePanel, "Delay (ms):", delaySpinner);

        // set volume (with slider)
        addComponents(homePanel, "Volume:", volumeSlider);

        // checkboxes
        addComponents(homePanel, sortAscendingCheckBox, muteCheckBox);

        // sort + stop
        addComponents(homePanel, sortButton, stopButton, shuffleButton);
    }

    private void initialiseGraphicsPanel() {
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

        SpinnerModel barBorderWidthSpinnerModel = new SpinnerNumberModel(2, 0, Integer.MAX_VALUE, 1);
        barBorderWidthSpinner = new JSpinner(barBorderWidthSpinnerModel);
        barBorderWidthSpinner.addChangeListener(e -> {
            BarPanel.barBorderWidth = (int)barBorderWidthSpinner.getValue();
            BarPanel.refresh();
        });

        JCheckBox borderActiveCheckBox = new JCheckBox("Border", true);
        borderActiveCheckBox.addActionListener(e -> {
            view.setBorderActive(borderActiveCheckBox.isSelected());
        });

        JCheckBox mergeBordersCheckBox = new JCheckBox("Merge Borders", false);
        mergeBordersCheckBox.addActionListener(e -> {
            BarPanel.mergeBorders = mergeBordersCheckBox.isSelected();
            BarPanel.refresh();
        });

        SpinnerModel marginSizeSpinnerModel = new SpinnerNumberModel(10, 0, Integer.MAX_VALUE, 1);
        JSpinner marginSizeSpinner = new JSpinner(marginSizeSpinnerModel);
        marginSizeSpinner.addChangeListener(e -> {
            BarPanel.marginSize = (int)marginSizeSpinner.getValue();
            BarPanel.refresh();
        });

        JCheckBox highlightCompareCheckBox = new JCheckBox("Highlight Comparing", true);
        highlightCompareCheckBox.addActionListener(e -> {
            BarPanel.highlightCompare = highlightCompareCheckBox.isSelected();
            BarPanel.refresh();
        });

        JCheckBox doneAnimationCheckBox = new JCheckBox("Done Animation", true);
        doneAnimationCheckBox.addActionListener(e -> {
            BarPanel.doneAnimation = doneAnimationCheckBox.isSelected();
            BarPanel.stopDoneAnimation();
        });

        // margin
        addComponents(graphicsPanel, "Margin size:", marginSizeSpinner);

        // select colors
        addComponents(graphicsPanel, "Bar color:", barColorDropDown);
        addComponents(graphicsPanel, "Bar comparing color:", barComparingColorDropDown);
        addComponents(graphicsPanel, "Bar done color:", barDoneColorDropDown);
        addComponents(graphicsPanel, "Bar border color:", barBorderColorDropDown);
        addComponents(graphicsPanel, "Bar background color:", barBackgroundColorDropDown);
        
        // border settings
        addComponents(graphicsPanel, "Bar border width:", barBorderWidthSpinner);
        addComponents(graphicsPanel, borderActiveCheckBox, mergeBordersCheckBox);

        // toggle animations
        addComponents(graphicsPanel, highlightCompareCheckBox, doneAnimationCheckBox);
    }

    private void initialiseSoundPanel() {
        JComboBox<Oscillator.Wave> soundWaveDropDown = new JComboBox<>(Oscillator.Wave.values());
        soundWaveDropDown.setSelectedItem(ColorOption.Black);
        soundWaveDropDown.addItemListener(e -> {
            Oscillator.wave = (Oscillator.Wave)(e.getItem());
        });

        SpinnerModel minFreqSpinnerModel = new SpinnerNumberModel(200, 200, 1600, 1);
        SpinnerModel maxFreqSpinnerModel = new SpinnerNumberModel(1200, 200, 1600, 1);
        JSpinner maxFreqSpinner = new JSpinner(maxFreqSpinnerModel);
        JSpinner minFreqSpinner = new JSpinner(minFreqSpinnerModel);
        minFreqSpinner.addChangeListener(e -> {
            int newMinFreq = (int)minFreqSpinner.getValue();
            int maxFreq = (int)maxFreqSpinner.getValue();
            if (newMinFreq > maxFreq) {
                minFreqSpinner.setValue(maxFreq);
                newMinFreq = maxFreq;
            }
            Sound.minFrequency = newMinFreq;
        });
        maxFreqSpinner.addChangeListener(e -> {
            int minFreq = (int)minFreqSpinner.getValue();
            int newMaxFreq = (int)maxFreqSpinner.getValue();
            if (newMaxFreq < minFreq) {
                maxFreqSpinner.setValue(minFreq);
                newMaxFreq = minFreq;
            }
            Sound.maxFrequency = newMaxFreq;
        });

        JComboBox<NormalisedScaler> freqScalingDropDown = new JComboBox<>(NormalisedScaler.values());
        freqScalingDropDown.setSelectedItem(NormalisedScaler.Linear);
        freqScalingDropDown.addItemListener(e -> {
            Sound.frequencyScaler = (NormalisedScaler)e.getItem();
        });

        SpinnerModel lengthMultiplierSpinnerModel = new SpinnerNumberModel(2.0, 0.0, 10.0, 0.1);
        JSpinner soundLengthMultiplierSpinner = new JSpinner(lengthMultiplierSpinnerModel);
        soundLengthMultiplierSpinner.addChangeListener(e -> {
            Sound.sustain = (double)soundLengthMultiplierSpinner.getValue();
        });

        JSlider attackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);
        attackSlider.addChangeListener(e -> { Oscillator.attack = attackSlider.getValue() / 100.0; });

        JSlider holdSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 45);
        holdSlider.addChangeListener(e -> { Oscillator.hold = holdSlider.getValue() / 100.0; });

        JSlider decaySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        decaySlider.addChangeListener(e -> { Oscillator.decay = decaySlider.getValue() / 100.0; });

        JSlider sustainSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 90);
        sustainSlider.addChangeListener(e -> { Oscillator.sustain = sustainSlider.getValue() / 100.0; });

        JSlider releaseSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        releaseSlider.addChangeListener(e -> { Oscillator.release = releaseSlider.getValue() / 100.0; });

        // sound settings
        addComponents(soundPanel, "Minimum frequency:", minFreqSpinner);
        addComponents(soundPanel, "Maximum frequency:", maxFreqSpinner);
        addComponents(soundPanel, "Frequency scaler:", freqScalingDropDown);
        addComponents(soundPanel, "Sound wave:", soundWaveDropDown);

        addComponents(soundPanel, "Sound length multiplier:", soundLengthMultiplierSpinner);

        addComponents(soundPanel, "Attack:", attackSlider);
        addComponents(soundPanel, "Hold:", holdSlider);
        addComponents(soundPanel, "Decay:", decaySlider);
        addComponents(soundPanel, "Sustain:", sustainSlider);
        addComponents(soundPanel, "Release:", releaseSlider);
    }

    private void addComponents(JPanel panel, String labelText, Component component) {
        JLabel label = new JLabel(labelText);
        addComponents(panel, label, component);
    }

    private void addComponents(JPanel panel, Component... components) {
        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        for (Component c : components) {
            container.add(c);
        }
        container.setMaximumSize(container.getPreferredSize());
        panel.add(container);
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
