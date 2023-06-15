import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mvc.*;

public class App {
    final int DEFAULT_ARRAY_LENGTH = 200;

    App() {
        Model model = new Model();

        JFrame frame = new JFrame("Choice");
        frame.setPreferredSize(new Dimension(500, 250));
        JPanel panel = new JPanel(new GridLayout(1, 2));

        JButton cliButton = new JButton("CLI");
        cliButton.addActionListener(e -> {
            frame.setVisible(false);
            IView view = new CLIView();
            Controller controller = new Controller(model, view);
            model.generateList(DEFAULT_ARRAY_LENGTH);
            view.initialise(model, controller);
        });
        panel.add(cliButton);

        JButton guiButton = new JButton("GUI");
        guiButton.addActionListener(e -> {
            frame.setVisible(false);
            IView view = new GUIView();
            Controller controller = new Controller(model, view);
            model.generateList(DEFAULT_ARRAY_LENGTH);
            view.initialise(model, controller);
        });
        panel.add(guiButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
