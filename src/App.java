import mvc.*;

public class App {
    App() {
        Model model = new Model();
        IView view = new CLIView();
        Controller controller = new Controller(model, view);

        model.generateList(10);
        view.initialise(model, controller);
    }

    public static void main(String[] args) {
        new App();
    }
}
