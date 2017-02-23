package br.com.fandrauss.fx.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * Utility class for easy window creation
 *
 * @author Fernando Andrauss
 */
public abstract class WindowControllerFx implements Initializable {

    private final StringProperty title = new SimpleStringProperty();
    private Region rootWindowPane;
    private Window parent;
    private Image icon;
    private Stage stage;
    private Modality modality;
    private StageStyle stageStyle;
    private boolean wait = false;
    private ShowEffect effectType;
    private Effect oldEffectParent;
    private Effect showEffect;

    private final String ERROR_MSG = "This method can not be called before window creation";

    public enum ShowEffect {
        BLUR, COLOR_ADJUST;
    }

    public WindowControllerFx() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Do nothing
    }

    /**
     * Defines stage icon
     *
     * @param icon
     * @return
     */
    public WindowControllerFx setIcon(Image icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Defines stage modality
     *
     * @param modality
     * @see Modality
     * @return
     */
    public WindowControllerFx setModality(Modality modality) {
        this.modality = modality;
        return this;
    }

    /**
     * Defines the stage parent
     *
     * @param parent
     * @return
     */
    public WindowControllerFx setParent(Window parent) {
        this.parent = parent;
        return this;
    }

    /**
     * If you have your own stage you can pass here. Normally used for the
     * primary stage
     *
     * @param stage custom stage
     * @return
     */
    public WindowControllerFx setStage(Stage stage) {
        this.stage = stage;
        return this;
    }

    /**
     * Enable showAndWait
     *
     * @return
     */
    public WindowControllerFx setWait() {
        this.wait = true;
        return this;
    }

    /**
     * Defines the predefined effect to be applied on parent on show this stage
     *
     * @param effect
     * @return
     */
    public WindowControllerFx setShowEffect(ShowEffect effect) {
        this.effectType = effect;
        return this;
    }

    /**
     * Defines the custom effect to be applied on parent on show this stage
     *
     * @param effect
     * @return
     */
    public WindowControllerFx setShowEffect(Effect effect) {
        this.showEffect = effect;
        return this;
    }

    /**
     * Maximize stage
     *
     * @return
     */
    public WindowControllerFx setMaximized() {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setMaximized(true);
        return this;
    }

    /**
     * Defines the stage height. Note: can only called after window creation
     *
     * @param height
     * @return
     */
    public WindowControllerFx setHeight(double height) {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setHeight(height);
        return this;
    }

    /**
     * Defines the stage width. Note: can only called after window creation
     *
     * @param width
     * @return
     */
    public WindowControllerFx setWidth(double width) {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setWidth(width);
        return this;
    }

    /**
     * Minimize stage
     *
     * @return
     */
    public WindowControllerFx setMinimized() {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setMaximized(false);
        return this;
    }

    /**
     * Switch to fullscreen mode
     *
     * @return
     */
    public WindowControllerFx fullScreen() {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setFullScreen(true);
        return this;
    }

    /**
     * exit fullscreen mode
     *
     * @return
     */
    public WindowControllerFx exitFullSreen() {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setFullScreen(false);
        return this;
    }

    /**
     * Get the stage title
     *
     * @return
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Defines the stage title
     *
     * @param value
     * @return
     */
    public WindowControllerFx setTitle(String value) {
        title.set(value);
        return this;
    }

    /**
     * Get the title property
     *
     * @return
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * This method should be overriden
     *
     * @return path of FXML layout resource
     */
    public String getFXML() {
        return null;
    }

    /**
     * This method should be overriden
     *
     * @return region with window content
     */
    public Region getRootPane() {
        return null;
    }

    /**
     * This method load the FXML content defined on getFXML or load the region
     * content defined on getRootPane method
     *
     * @throws Exception FXML load exceptions or RuntimeException if FXML and
     * RootPane not defined
     */
    private void loadView() throws Exception {

        if (getFXML() != null && getRootPane() == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(getFXML()));
            // define the subclass as controller

            // override any possible controller defined on FXML
            loader.setControllerFactory((param) -> this);

            // load the content
            try {
                rootWindowPane = loader.load();
            } catch (Exception err) {
                // If the FXML not have specified controller defines this as controller
                if (err.getMessage().contains("No controller specified")) {

                    // Try load FXML again setting this as controller
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(getFXML()));
                    loader.setController(this);
                    rootWindowPane = loader.load();

                } else {
                    // If the error not caused by controller omission rethrow the error
                    throw err;
                }
            }

        } else if (getRootPane() != null) {
            rootWindowPane = getRootPane();
        } else {
            throw new RuntimeException("The content source is not defined, you must override getFXML or getRootPane!");
        }
    }

    public WindowControllerFx show() {
        stageStyle = StageStyle.DECORATED;
        createStage();
        if (wait) {
            stage.showAndWait();
        } else {
            stage.show();
        }

        onShow();

        return this;
    }

    public WindowControllerFx showModal() {
        stageStyle = StageStyle.DECORATED;
        modality = modality != null ? modality : Modality.WINDOW_MODAL;
        createStage();

        if (wait) {
            stage.showAndWait();
        } else {
            stage.show();
        }

        onShow();
        return this;
    }

    public WindowControllerFx showUndecorated(boolean transparent) {
        stageStyle = StageStyle.UNDECORATED;
        createStage();

        if (transparent) {
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getScene().setFill(Color.TRANSPARENT);
            rootWindowPane.setStyle("-fx-background-color: transparent;");
        }

        if (wait) {
            stage.showAndWait();
        } else {
            stage.show();
        }

        onShow();
        return this;
    }

    public WindowControllerFx showAsDialg() {
        modality = modality != null ? modality : Modality.WINDOW_MODAL;
        stageStyle = StageStyle.UTILITY;
        createStage();
        if (wait) {
            stage.showAndWait();
        } else {
            stage.show();
        }
        onShow();
        return this;
    }

    public Stage createStage() {
        try {
            modality = modality != null ? modality : Modality.NONE;
            loadView();

            if (stage == null) {

                stage = createStageFromContent(parent, rootWindowPane, title.get(), stageStyle, modality);
                stage.titleProperty().bind(title);
                stage.sizeToScene();

                if (icon != null) {
                    stage.getIcons().add(icon);
                }
            } else {
                stage.setScene(new Scene(rootWindowPane));
            }

            applyShowEffect();

            return stage;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load window", ex);
        }
    }

    public Stage getWindow() {
        return stage;
    }

    /**
     * This method can be override, is called when window is showing
     */
    public void onShow() {

    }

    /**
     * Apply the show effect on the window parent (if exist)
     */
    private void applyShowEffect() {

        if (effectType != null) {
            switch (effectType) {
                case BLUR: {
                    showEffect = new BoxBlur();
                    break;
                }
                case COLOR_ADJUST: {
                    showEffect = new ColorAdjust(0, 0, -0.52, 0);
                    break;
                }
            }
        }

        if (showEffect == null) {
            return;
        }

        // Define blur on show
        getWindow().setOnShown((evt) -> {
            if (parent != null) {
                oldEffectParent = parent.getScene().getRoot().getEffect();
                parent.getScene().getRoot().setEffect(showEffect);
            }
        });

        // Remove blur on hide 
        getWindow().setOnHiding((event) -> {
            if (parent != null) {
                parent.getScene().getRoot().setEffect(oldEffectParent);
            }
        });

    }

    public Stage createStageFromContent(Window parent, Parent content, String titulo, StageStyle style, Modality modal) {
        Stage s = new Stage(style);

        Scene scene = new Scene(content);
        s.setScene(scene);
        s.initOwner(parent);
        s.initModality(modal);
        s.setTitle(titulo);

        return s;
    }
}
