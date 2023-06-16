package mvc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
        frame.setMinimumSize(new Dimension(640, 360));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        frame.add(mainPanel);

        optionsPanel = new OptionsPanel(controller);
        optionsPanel.setPreferredSize(new Dimension(256, 720));
        optionsPanel.setBackground(Color.GREEN);
        mainPanel.add(optionsPanel);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.25;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(optionsPanel, constraints);

        barsPanel = new BarPanel(model, controller, optionsPanel);
        barsPanel.setPreferredSize(new Dimension(1024, 720));
        barsPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainPanel.add(barsPanel);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.75;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(barsPanel, constraints);

        frame.pack();
        frame.setVisible(true);

        refreshView();
    }

    @Override
    public void refreshView() {
        barsPanel.repaint();
    }
    
}
