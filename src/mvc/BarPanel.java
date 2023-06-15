package mvc;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BarPanel extends JPanel {
    final int PADDING = 10;

    Model model;
    boolean hasBorder = false;

    public BarPanel(Model m) {
        model = m;
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

        int maxHeight = getSize().height - 2 * PADDING;

        int[] nums = model.getList();
        for (int i = 0; i < model.getArrayLength(); i++) {
            int barHeight = (int)(maxHeight * ((double)nums[i] / (double)(model.getArrayLength() + 1)));
            int barWidth = baseBarWidth;
            if (spareWidthPixels > 0) {
                barWidth++;
                spareWidthPixels--;
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
