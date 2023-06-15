package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BarPanel extends JPanel {
    final int PADDING = 10;

    Model model;
    boolean hasBorder = true;

    // TODO: Add functionality to make this change when the number of bars changes
    int[] barSample;

    public BarPanel(Model m) {
        model = m;

        barSample = m.getList();
    }

    public void setBorderActive(boolean b) {
        hasBorder = b;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int baseBarWidth = (getSize().width - 2 * PADDING) / model.getArrayLength();
        int spareWidthPixels = (getSize().width - 2 * PADDING) % model.getArrayLength();
        int x = PADDING;

        ArrayList<Integer> barsWithExtraPixels = new ArrayList<>();
        for (int i = 0; i < spareWidthPixels; i++) {
            barsWithExtraPixels.add(barSample[i]);
        }

        int maxHeight = getSize().height - 2 * PADDING;

        int[] nums = model.getList();
        for (int i = 0; i < model.getArrayLength(); i++) {
            int barHeight = (int)(maxHeight * ((double)nums[i] / (double)(model.getArrayLength() + 1)));
            int barWidth = baseBarWidth;
            if (barsWithExtraPixels.contains(nums[i])) {
                barWidth++;
            }

            int y = PADDING + maxHeight - barHeight;

            g.setColor(Color.BLACK);
            g.fillRect(x, y, barWidth, barHeight);

            if (hasBorder) {
                g.setColor(Color.WHITE);
                g.drawRect(x, y, barWidth, barHeight);
            }

            x += barWidth;
        }
    }
}
