
import br.com.fandrauss.fx.gui.WindowControllerFx;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.SepiaTone;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Fernando Andrauss
 */
public class WindowTestController extends WindowControllerFx {

    @FXML
    private Button btnShow;

    @FXML
    private Button btnShowAsDialog;

    @FXML
    private Button btnShowModal;

    @FXML
    private Button btnShowUndecorated;

    @FXML
    private Button btnShowUndecoratedTransp;

    @FXML
    private Button btnShowCustom;

    @FXML
    private Label lbParam;

    private String param;

    public WindowTestController() {
    }

    public WindowTestController(Window parent) {
        setParent(parent);
    }

    public WindowTestController(String param) {
        this.param = param;
    }

    @Override
    public String getFXML() {
        return "/window-test.fxml";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (param != null) {
            lbParam.setText("Param --> " + param);
        }

        btnShow.setOnAction((e) -> {
            new WindowTestController(getWindow())
                    .setShowEffect(new Bloom())
                    .show();
        });

        btnShowAsDialog.setOnAction((e) -> {
            new WindowTestController("Hello WindowControllerFx")
                    .setTitle("WindowFX")
                    .setShowEffect(ShowEffect.COLOR_ADJUST)
                    .setParent(getWindow())
                    .showAsDialg();
        });

        btnShowModal.setOnAction((e) -> {
            new WindowTestController()
                    .setParent(getWindow())
                    .setShowEffect(ShowEffect.BLUR)
                    .showModal();
        });

        btnShowUndecorated.setOnAction((e) -> {
            new WindowTestController()
                    .setParent(getWindow())
                    .showUndecorated(false);
        });

        btnShowUndecoratedTransp.setOnAction((e) -> {
            new WindowTestController()
                    .setParent(getWindow())
                    .showUndecorated(true);
        });

        btnShowCustom.setOnAction((e) -> {
            new WindowControllerFx() {
                @Override
                public Region getRootPane() {

                    BorderPane pane = new BorderPane();

                    MenuItem exitMenu = new MenuItem("Go out");
                    exitMenu.setOnAction((evt) -> {
                        getWindow().close();
                    });

                    MenuBar bar = new MenuBar(
                            new Menu("File"),
                            new Menu("Edit"),
                            new Menu("Exit", null,
                                    exitMenu
                            )
                    );

                    pane.setTop(bar);

                    pane.setPrefSize(500, 500);

                    pane.setCenter(new TextArea("Some text"));

                    return pane;
                }
            }
                    .setShowEffect(new SepiaTone())
                    .setParent(getWindow())
                    .showModal();
        });

    }

    @FXML
    private void CloseWindow() {
        getWindow().close();
    }

}
