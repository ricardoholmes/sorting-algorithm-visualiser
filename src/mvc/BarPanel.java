package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BarPanel extends JPanel {
    final int PADDING = 10;

    private static Model model;
    private static Controller controller;
    private static OptionsPanel optionsPanel;
    private boolean hasBorder = true;

    private static int[] barSample;

    public BarPanel(Model m, Controller c, OptionsPanel options) {
        model = m;
        controller = c;
        optionsPanel = options;
        resetBarSample();
    }

    public static void resetBarSample() {
        barSample = model.getList();
    }

    public void setBorderActive(boolean b) {
        hasBorder = b;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int maxBars = getSize().width / (hasBorder ? 3 : 1);
        optionsPanel.setMaximumBarCount(maxBars);
        
        if (model.getArrayLength() > maxBars) {
            controller.generateList(maxBars);
        }

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
