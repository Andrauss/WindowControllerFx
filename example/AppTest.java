
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Fernando Andrauss
 */
public class AppTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new WindowTestController()
                .setStage(primaryStage)
                .setTitle("WindowControllerFX")
                .show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
