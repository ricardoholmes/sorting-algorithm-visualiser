package sortalgorithmvisualiser.mvc;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sortalgorithmvisualiser.sorters.Sorter;

public class StatsPanel extends JPanel {
    private Model model;
    private GUIView view;

    private JLabel barCountLabel;
    private JLabel delayLabel;
    private JLabel accessesLabel;
    private JLabel assignmentsLabel;
    private JLabel comparisonsLabel;

    private JLabel barWidthLabel;
    private JLabel barsPerHeightLabel;
    private JLabel mainWindowDimensionsLabel;
    private JLabel barPanelDimensionsLabel;
    private JLabel optionsPanelDimensionsLabel;

    public StatsPanel(Model m, GUIView v) {
        model = m;
        view = v;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel container = new JPanel(new GridLayout(0, 2, 0, 5));
        add(container, BorderLayout.PAGE_START);

        barCountLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Bar count:", JLabel.LEFT));
        container.add(barCountLabel);

        delayLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Delay:", JLabel.LEFT));
        container.add(delayLabel);

        accessesLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Array accesses:", JLabel.LEFT));
        container.add(accessesLabel);

        comparisonsLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Comparisons:", JLabel.LEFT));
        container.add(comparisonsLabel);

        assignmentsLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Assignments:", JLabel.LEFT));
        container.add(assignmentsLabel);

        barWidthLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Bar width:", JLabel.LEFT));
        container.add(barWidthLabel);

        barsPerHeightLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Bars per height:", JLabel.LEFT));
        container.add(barsPerHeightLabel);

        mainWindowDimensionsLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Main window:", JLabel.LEFT));
        container.add(mainWindowDimensionsLabel);

        barPanelDimensionsLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Bar panel:", JLabel.LEFT));
        container.add(barPanelDimensionsLabel);

        optionsPanelDimensionsLabel = new JLabel("", JLabel.RIGHT);
        container.add(new JLabel("Options panel:", JLabel.LEFT));
        container.add(optionsPanelDimensionsLabel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int barCount = model.getArrayLength();
        barCountLabel.setText(Integer.toString(barCount));

        String delayText = getDelayText();
        delayLabel.setText(delayText);

        int accesses = model.getAccessesCount();
        accessesLabel.setText(Integer.toString(accesses));

        int comparisons = Sorter.getComparisonsCount();
        comparisonsLabel.setText(Integer.toString(comparisons));

        int assignments = model.getAssignmentsCount();
        assignmentsLabel.setText(Integer.toString(assignments));

        // graphics/visuals stats

        Double barWidth = BarPanel.getBarWidth();
        barWidthLabel.setText(String.format("%.2f pixels", barWidth));

        Double barsPerHeight = BarPanel.getBarsPerHeight();
        barsPerHeightLabel.setText(String.format("%.2f", barsPerHeight));

        String mainWindowDimensions = view.getMainWindowDimensions();
        mainWindowDimensionsLabel.setText(mainWindowDimensions);

        String barPanelDimensions = view.getBarPanelDimensions();
        barPanelDimensionsLabel.setText(barPanelDimensions);

        String optionsPanelDimensions = view.getOptionsPanelDimensions();
        optionsPanelDimensionsLabel.setText(optionsPanelDimensions);
    }

    private String getDelayText() {
        double delay = Controller.currentDelay;
        if (delay >= 1000) {
            delay /= 1000;
            return String.format("%.1f", delay) + "s";
        }

        if (delay >= 0.01) {
            return String.format("%.1f", delay) + "ms";
        }

        delay *= 1000;
        if (delay >= 0.01) {
            return String.format("%.1f", delay) + "μs";
        }

        delay *= 1000;
        return String.format("%.1f", delay) + "ns";
    }
}
