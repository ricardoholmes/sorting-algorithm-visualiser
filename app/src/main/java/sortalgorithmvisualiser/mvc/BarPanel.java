package sortalgorithmvisualiser.mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static int barBorderWidth = 2;
    public static boolean mergeBorders = false;

    public static boolean doneAnimation = true;
    public static boolean highlightCompare = true;
    
    public static int marginSize = 10;

    private static List<Integer> shuffledIndices;

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

        shuffledIndices = IntStream
                .rangeClosed(1, model.getArrayLength())
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(shuffledIndices);

        refresh();
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

        if (!doneAnimation) {
            return;
        }

        for (int i = 0; i < model.getArrayLength(); i++) {
            sortedCount++;

            double delay = Controller.currentDelay;

            if (i + 1 < model.getArrayLength()) {
                controller.setComparing(i, i+1);
            }

            repaint();

            long millis = (long)delay;
            int nanos = (int)((delay % 1) * 1_000_000);

            try {
                Thread.sleep(0, nanos);
            } catch (InterruptedException e) { }

            for (int t = 0; t < millis; t++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) { }

                if (stopDoneAnim || !doneAnimation) {
                    sortedCount = 0;
                    repaint();
                    return;
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(barBackgroundColor);

        int panelWidth = getSize().width - (marginSize * 2);

        int maxBars = panelWidth;
        if (hasBorder && mergeBorders) {
            maxBars -= barBorderWidth;
            maxBars /= 1 + barBorderWidth;
        }
        else if (hasBorder) {
            maxBars /= 1 + (2 * barBorderWidth);
        }
        int maxBorderWidth = (panelWidth - 1) / 2;
        optionsPanel.setMaximums(maxBars, maxBorderWidth);

        if (barBorderWidth > maxBorderWidth) {
            barBorderWidth = maxBorderWidth;
        }
        
        if (model.getArrayLength() > maxBars) {
            controller.generateList(maxBars);
        }

        int barCount = model.getArrayLength();

        int baseBarWidth = panelWidth / barCount;
        int spareWidthPixels = panelWidth % barCount;
        int x = marginSize;

        ArrayList<Integer> barsWithExtraPixels = new ArrayList<>();

        for (int i = 0; i < spareWidthPixels; i++) {
            int barIndex = shuffledIndices.get(i);
            if (barIndex >= barCount) {
                spareWidthPixels++; // skip the index, otherwise would throw an error
            }
            else {
                barsWithExtraPixels.add(barIndex);
            }
        }

        int maxHeight = getSize().height - (marginSize * 2);

        int tempSortedCount = sortedCount;

        int[] nums = model.getList();
        for (int i = 0; i < barCount; i++) {
            int barHeight = (int)(maxHeight * ((double)nums[i] / model.getMaxValueAtCreation()));
            int barWidth = baseBarWidth;
            if (barsWithExtraPixels.contains(nums[i])) {
                barWidth++;
            }

            int y = marginSize + (maxHeight - barHeight);

            boolean hasVisibleBorder = hasBorder && barBorderWidth > 0;
            if (hasVisibleBorder) {
                g.setColor(barBorderColor);

                if (mergeBorders) {
                    int borderX = x;
                    int width = barWidth + barBorderWidth;
                    if (i > 0) {
                        borderX -= barBorderWidth / 2;
                    }
                    if (i == 0 || i == barCount - 1) {
                        width -= (barBorderWidth + 1) / 2;
                    }
                    g.fillRect(borderX, y, width, barHeight);
                }
                else {
                    g.fillRect(x, y, barWidth, barHeight);
                }
            }

            if (doneAnimation && tempSortedCount > 0) {
                g.setColor(barDoneColor);
                tempSortedCount--;
            }
            else if (highlightCompare && barsComparing.contains(i)) {
                g.setColor(barComparingColor);
            }
            else {
                g.setColor(barColor);
            }

            if (hasVisibleBorder) {
                int barX = x + barBorderWidth;
                int barY = y + barBorderWidth;
                int width = barWidth - (2 * barBorderWidth);
                int height = barHeight - (2 * barBorderWidth);

                if (mergeBorders) {
                    width += barBorderWidth;
                    if (i > 0) {
                        barX -= (barBorderWidth + 1) / 2;
                    }

                    if (i == 0 || i == barCount - 1) {
                        width -= (barBorderWidth + 1) / 2;
                    }
                }

                g.fillRect(barX, barY, width, height);
            }
            else {
                g.fillRect(x, y, barWidth, barHeight);
            }

            x += barWidth;
        }
    }
}
