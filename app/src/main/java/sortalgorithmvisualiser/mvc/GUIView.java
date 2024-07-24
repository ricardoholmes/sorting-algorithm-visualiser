package sortalgorithmvisualiser.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUIView implements IView {
    private Model model;
    private Controller controller;

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JScrollPane scrollableOptionsPane;
    private OptionsPanel optionsPanel;
    private BarPanel barsPanel;

    private JFrame optionsFrame;

    public boolean optionsPoppedOut;

    @Override
    public void initialise(Model m, Controller c) {
        model = m;
        controller = c;

        mainFrame = new JFrame("Sorting Algorithm Visualiser");
        mainFrame.setPreferredSize(new Dimension(1280, 720));
        mainFrame.setMinimumSize(new Dimension(640, 360));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainFrame.add(mainPanel);
        
        optionsPanel = new OptionsPanel(controller, this, model);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        scrollableOptionsPane = new JScrollPane(optionsPanel);

        barsPanel = new BarPanel(model, controller, optionsPanel);
        barsPanel.setPreferredSize(new Dimension(960, 720));

        initialiseMainPanel();

        mainFrame.pack();
        mainFrame.setVisible(true);

        refreshView();

        optionsPoppedOut = false;
    }

    private void initialiseMainPanel() {
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        // options panel
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.25;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(scrollableOptionsPane, constraints);

        // bars panel
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.75;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(barsPanel, constraints);
    }

    public void setBorderActive(boolean b) {
        barsPanel.setBorderActive(b);
        refreshView();
    }

    @Override
    public void refreshView() {
        barsPanel.repaint();
    }

    @Override
    public void doneSorting() {
        barsPanel.doneSorting();
        Sound.stopSound();
    }

    public void popOutOptions() {
        if (optionsFrame != null) {
            optionsPoppedOut = true;
            return;
        }

        int width = scrollableOptionsPane.getWidth();
        int height = scrollableOptionsPane.getHeight();

        mainPanel.remove(scrollableOptionsPane);
        mainPanel.remove(barsPanel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(barsPanel, BorderLayout.CENTER);

        optionsFrame = new JFrame("Options");
        optionsFrame.setMinimumSize(new Dimension(160, 360));
        optionsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        optionsFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                popInOptions();
            }
        });

        optionsFrame.add(scrollableOptionsPane);

        scrollableOptionsPane.setPreferredSize(new Dimension(width, height));

        optionsFrame.pack();
        optionsFrame.setVisible(true);

        mainFrame.revalidate();
        mainFrame.repaint();

        refreshView();

        optionsPoppedOut = true;
    }

    public void popInOptions() {
        if (optionsFrame == null) {
            optionsPoppedOut = false;
            return;
        }

        optionsFrame.remove(scrollableOptionsPane);
        optionsFrame.dispose();
        optionsFrame = null;

        mainPanel.remove(barsPanel);

        initialiseMainPanel();

        mainFrame.revalidate();
        mainFrame.repaint();

        refreshView();

        optionsPoppedOut = false;
    }
}
