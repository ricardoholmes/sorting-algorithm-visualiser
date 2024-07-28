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

    private OptionsPanel optionsPanel;
    private BarPanel barPanel;

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

        barPanel = new BarPanel(model, controller, optionsPanel);

        initialiseMainPanel();

        mainFrame.pack();
        mainFrame.setVisible(true);

        refreshView();

        optionsPoppedOut = false;
    }

    private void initialiseMainPanel() {
        mainPanel.add(optionsPanel, BorderLayout.WEST);
        mainPanel.add(barPanel, BorderLayout.CENTER);
    }

    public void setBorderActive(boolean b) {
        barPanel.setBorderActive(b);
        refreshView();
    }

    @Override
    public void refreshView() {
        barPanel.repaint();
        optionsPanel.repaint();
    }

    @Override
    public void doneSorting() {
        barPanel.doneSorting();
        Sound.stopSound();
        optionsPanel.doneSorting();
    }

    public void popOutOptions() {
        if (optionsFrame != null) {
            optionsPoppedOut = true;
            return;
        }
        
        optionsPanel.popOutButton.setText("Pop In");

        mainPanel.remove(barPanel);

        mainPanel.add(barPanel, BorderLayout.CENTER);

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

        if (mainFrame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            optionsFrame.setLocation(mainFrame.getLocation());
        }

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

        optionsPanel.popOutButton.setText("Pop Out");

        optionsFrame.remove(optionsPanel);
        optionsFrame.dispose();
        optionsFrame = null;

        mainPanel.remove(barPanel);

        initialiseMainPanel();

        mainFrame.revalidate();
        mainFrame.repaint();

        refreshView();

        optionsPoppedOut = false;
    }

    public String getMainWindowDimensions() {
        int w = mainPanel.getWidth();
        int h = mainPanel.getHeight();
        return w + "x" + h;
    }

    public String getBarPanelDimensions() {
        int w = barPanel.getWidth() - (BarPanel.marginSize * 2);
        int h = barPanel.getHeight() - (BarPanel.marginSize * 2);
        return w + "x" + h;
    }

    public String getOptionsPanelDimensions() {
        int w = optionsPanel.getWidth();
        int h = optionsPanel.getHeight();
        return w + "x" + h;
    }
}
