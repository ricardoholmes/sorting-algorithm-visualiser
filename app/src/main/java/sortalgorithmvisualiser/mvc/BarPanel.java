package sortalgorithmvisualiser.mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BarPanel extends JPanel {
    private static Model model;
    private static Controller controller;
    private static OptionsPanel optionsPanel;
    private boolean hasBorder = true;

    private static double maxValue;

    private static int sortedCount = 0;
    private static boolean stopDoneAnim = false;

    public BarPanel(Model m, Controller c, OptionsPanel options) {
        model = m;
        controller = c;
        optionsPanel = options;
        resetBarSample();
        resetBarColor();
    }

    public static void resetBarSample() {
        maxValue = model.getArrayLength();
    }

    public static void resetBarColor() {
        sortedCount = 0;
        stopDoneAnim = false;
    }

    public static void stopDoneAnimation() {
        stopDoneAnim = true;
    }

    public void setBorderActive(boolean b) {
        hasBorder = b;
    }

    public void doneSorting() {
        for (int i = 1; i <= model.getArrayLength(); i++) {
            sortedCount = i;
            repaint();

            if (!OptionsPanel.isMuted()) {
                final int x = i - 1;
                controller.playSoundForIndex(x, (int)Controller.currentDelay);
            }
            try {
                Thread.sleep((int)Controller.currentDelay);
            } catch (InterruptedException e) { }

            if (stopDoneAnim) {
                return;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int maxBars = getSize().width / (hasBorder ? 3 : 1);
        optionsPanel.setMaximumBarCount(maxBars);
        
        if (model.getArrayLength() > maxBars) {
            controller.generateList(maxBars);
        }

        int baseBarWidth = getSize().width / model.getArrayLength();
        int spareWidthPixels = getSize().width % model.getArrayLength();
        int x = 0;

        ArrayList<Integer> barsWithExtraPixels = new ArrayList<>();
        for (int i = 0; i < spareWidthPixels; i++) {
            barsWithExtraPixels.add(model.getValueAt(i));
        }

        int maxHeight = getSize().height;

        int tempSortedCount = sortedCount;

        int[] nums = model.getList();
        for (int i = 0; i < model.getArrayLength(); i++) {
            int barHeight = (int)(maxHeight * ((double)nums[i] / maxValue));
            int barWidth = baseBarWidth;
            if (barsWithExtraPixels.contains(nums[i])) {
                barWidth++;
            }

            int y = maxHeight - barHeight;

            if (tempSortedCount > 0) {
                g.setColor(Color.GREEN);
                tempSortedCount--;
            }
            else {
                g.setColor(Color.BLACK);
            }
            g.fillRect(x, y, barWidth, barHeight);

            if (hasBorder) {
                g.setColor(Color.WHITE);
                g.drawRect(x, y, barWidth, barHeight);
            }

            x += barWidth;
        }
    }
}
