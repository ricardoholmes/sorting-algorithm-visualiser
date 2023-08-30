package sortalgorithmvisualiser;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import sortalgorithmvisualiser.mvc.*;

public class App {
    final static int DEFAULT_ARRAY_LENGTH = 10;

    final static int DEFAULT_LAUNCH = 0;
    final static int LAUNCH_CLI = 1;
    final static int LAUNCH_GUI = 2;
    final static int INVALID_LAUNCH_OPTION = -1;

    App(int launchCode) {
        if (launchCode == DEFAULT_LAUNCH) {
            // if there's no console, launch to gui
            if (System.console() == null) {
                launchCode = LAUNCH_GUI;
            }

            // if gui is unsupported, launch to cli
            else if (GraphicsEnvironment.isHeadless()) {
                launchCode = LAUNCH_CLI;
            }
        }

        if (launchCode == DEFAULT_LAUNCH) {
            promptLaunchOptions();
            return;
        }

        IView view;
        if (launchCode == LAUNCH_CLI) {
            view = new CLIView();
        }
        else {
            view = new GUIView();
        }

        Model model = new Model();
        Controller controller = new Controller(model, view);
        model.generateList(DEFAULT_ARRAY_LENGTH);
        view.initialise(model, controller);
    }

    private void promptLaunchOptions() {
        Model model = new Model();

        JFrame frame = new JFrame("Choice");
        frame.setPreferredSize(new Dimension(500, 250));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        int startupCode = DEFAULT_LAUNCH;
        if (args.length == 1) {
            String arg = args[0];
            switch (arg) {
                case "--command-line-interface":
                case "-cli":
                case "cli":
                    startupCode = LAUNCH_CLI;
                    break;

                case "--graphical-user-interface":
                case "-gui":
                case "gui":
                    startupCode = LAUNCH_GUI;
                    break;

                case "--help":
                case "-help":
                case "help":
                case "--h":
                case "-h":
                    System.out.println("Usage: ./sorting-algorithm-visualiser [OPTION]\n");
                    System.out.println("Options:");
                    System.out.println("  -cli, --command-line-interface   \tLaunches directly to cli mode.");
                    System.out.println("  -gui, --graphical-user-interface \tLaunches directly to gui mode.");
                    return;
                
                default:
                    startupCode = INVALID_LAUNCH_OPTION;
                    break;
            }
        }
        else if (args.length > 1) {
            startupCode = INVALID_LAUNCH_OPTION;
        }

        if (startupCode == INVALID_LAUNCH_OPTION) {
            System.out.println("Invalid argument(s) provided.");
            System.out.println("Usage: ./sorting-algorithm-visualiser [OPTION]");
            System.out.println("Try './sorting-algorithm-visualiser --help' for more information.");
        }

        new App(startupCode);
    }
}
