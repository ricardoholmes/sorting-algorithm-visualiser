package sortalgorithmvisualiser.mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

public class BarPanel extends JPanel {
    private static Model model;
    private static Controller controller;
    private static OptionsPanel optionsPanel;
    private static BarPanel instance;

    private boolean hasBorder = true;

    private static int sortedCount = 0;
    private static boolean stopDoneAnim = false;

    private static List<Integer> barsComparing = new ArrayList<>();

    public static Color barColor = Color.BLACK;
    public static Color barComparingColor = Color.RED;
    public static Color barDoneColor = Color.GREEN;
    public static Color barBorderColor = Color.WHITE;
    public static Color barBackgroundColor = Color.WHITE;

    public BarPanel(Model m, Controller c, OptionsPanel options) {
        model = m;
        controller = c;
        optionsPanel = options;
        instance = this;
        resetBars();
    }

    public static void resetBars() {
        sortedCount = 0;
        stopDoneAnim = false;
        barsComparing = new ArrayList<>();
    }

    public static void stopDoneAnimation() {
        stopDoneAnim = true;
        sortedCount = 0;
        barsComparing = new ArrayList<>();
        refresh();
    }

    public void setBorderActive(boolean b) {
        hasBorder = b;
    }

    public static void comparingBars(int index1, int index2) {
        barsComparing = Arrays.asList(index1, index2);
    }

    public static void refresh() {
        instance.repaint();
    }

    public void doneSorting() {
        resetBars();
        for (int i = 0; i < model.getArrayLength(); i++) {
            sortedCount++;
            repaint();

            double delay = Controller.currentDelay;

            controller.playSoundForIndex(i, delay);

            long millis = (long)delay;
            int nanos = (int)((delay % 1) * 1_000_000);
            try {
                Thread.sleep(millis, nanos);
            } catch (InterruptedException e) { }

            if (stopDoneAnim) {
                return;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(barBackgroundColor);

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
            int barHeight = (int)(maxHeight * ((double)nums[i] / model.getMaxValueAtCreation()));
            int barWidth = baseBarWidth;
            if (barsWithExtraPixels.contains(nums[i])) {
                barWidth++;
            }

            int y = maxHeight - barHeight;

            if (tempSortedCount > 0) {
                g.setColor(barDoneColor);
                tempSortedCount--;
            }
            else if (barsComparing.contains(i)) {
                g.setColor(barComparingColor);
            }
            else {
                g.setColor(barColor);
            }
            g.fillRect(x, y, barWidth, barHeight);

            if (hasBorder) {
                g.setColor(barBorderColor);
                g.drawRect(x, y, barWidth, barHeight);
            }

            x += barWidth;
        }
    }
}
