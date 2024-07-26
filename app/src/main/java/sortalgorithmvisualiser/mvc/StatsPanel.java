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

    private JLabel barCountLabel;
    private JLabel delayLabel;
    private JLabel accessesLabel;
    private JLabel comparisonsLabel;

    public StatsPanel(Model m) {
        model = m;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel container = new JPanel(new GridLayout(0, 2, 0, 5));
        add(container, BorderLayout.PAGE_START);

        container.add(new JLabel("Bar count:", JLabel.LEFT));
        barCountLabel = new JLabel("", JLabel.RIGHT);
        container.add(barCountLabel);

        container.add(new JLabel("Delay:", JLabel.LEFT));
        delayLabel = new JLabel("", JLabel.RIGHT);
        container.add(delayLabel);

        container.add(new JLabel("Array accesses:", JLabel.LEFT));
        accessesLabel = new JLabel("", JLabel.RIGHT);
        container.add(accessesLabel);

        container.add(new JLabel("Comparisons:", JLabel.LEFT));
        comparisonsLabel = new JLabel("", JLabel.RIGHT);
        container.add(comparisonsLabel);
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
