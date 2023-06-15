package mvc;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel {
    public OptionsPanel(Controller c) {
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            c.selectSorter(0);
            c.sort(0);
        });

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            c.shuffle();
        });

        this.add(sortButton);
        this.add(shuffleButton);
    }
}
