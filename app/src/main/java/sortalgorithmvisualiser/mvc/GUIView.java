package sortalgorithmvisualiser.mvc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIView implements IView {
    Model model;
    Controller controller;

    JPanel mainPanel;
    OptionsPanel optionsPanel;
    BarPanel barsPanel;

    @Override
    public void initialise(Model m, Controller c) {
        model = m;
        controller = c;

        JFrame frame = new JFrame("Sorting Algorithm Visualiser");
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.setMinimumSize(new Dimension(854, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        frame.add(mainPanel);

        optionsPanel = new OptionsPanel(controller, this, model);
        optionsPanel.setMinimumSize(new Dimension(0, 0));
        optionsPanel.setPreferredSize(new Dimension(320, 720));
        optionsPanel.setBackground(Color.GREEN);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.25;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(optionsPanel, constraints);

        barsPanel = new BarPanel(model, controller, optionsPanel);
        barsPanel.setPreferredSize(new Dimension(1024, 720));
        barsPanel.setPreferredSize(new Dimension(960, 720));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 0.75;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(barsPanel, constraints);

        frame.pack();
        frame.setVisible(true);

        refreshView();
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
    }
}
