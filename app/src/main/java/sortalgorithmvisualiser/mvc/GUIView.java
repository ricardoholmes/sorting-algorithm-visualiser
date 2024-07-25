package sortalgorithmvisualiser.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIView implements IView {
    private Model model;
    private Controller controller;

    private JFrame mainFrame;
    private JPanel mainPanel;
    // private JScrollPane scrollableOptionsPane;
    private OptionsPanel optionsPanel;
    private BarPanel barsPanel;

    private JFrame optionsFrame;

    public boolean optionsPoppedOut;

    @Override
    public void initialise(Model m, Controller c) {
        model = m;
        controller = c;

        mainFrame = new JFrame("Sorting Algorithm Visualiser");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(1280, 720));
        mainFrame.setMinimumSize(new Dimension(640, 360));

        mainPanel = new JPanel(new BorderLayout());
        mainFrame.setContentPane(mainPanel);

        optionsPanel = new OptionsPanel(controller, model, this);

        barsPanel = new BarPanel(model, controller, optionsPanel);

        initialiseMainPanel();

        mainFrame.pack();
        mainFrame.setVisible(true);

        refreshView();

        optionsPoppedOut = false;
    }

    private void initialiseMainPanel() {
        mainPanel.add(optionsPanel, BorderLayout.WEST);
        mainPanel.add(barsPanel, BorderLayout.CENTER);
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

        mainPanel.remove(barsPanel);

        mainPanel.add(barsPanel, BorderLayout.CENTER);

        optionsFrame = new JFrame("Options");
        optionsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        optionsFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                popInOptions();
            }
        });

        optionsFrame.setContentPane(optionsPanel);
        optionsPanel.setPreferredSize(optionsPanel.getSize());

        optionsFrame.pack();
        optionsFrame.setVisible(true);

        mainFrame.revalidate();
        mainFrame.repaint();

        refreshView();
        optionsFrame.setMinimumSize(new Dimension(optionsFrame.getWidth(), 360));

        optionsPoppedOut = true;
    }

    public void popInOptions() {
        if (optionsFrame == null) {
            optionsPoppedOut = false;
            return;
        }

        optionsFrame.remove(optionsPanel);
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
