package br.com.fandrauss.fx.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.NamedArg;
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
    private boolean wait = false;
    private boolean blur = false;
    private ShowEffect effectType;
    private Effect oldEffectParent;
    private Effect showEffect;

    private final String ERROR_MSG = "This method can not be called before window creation";

    public enum ShowEffect {
        BLUR, COLOR_ADJUST;
    }

    /**
     * O construtor default deve existir para que o controller possa ser
     * instanciado pelo FXMLLoader no momento do carregamento do layout (no caso
     * de layout FXML)
     *
     * @see FXMLLoader
     */
    public WindowControllerFx() {

    }

    /**
     * Controller initialization method, this method is called when the screen
     * layout is loaded from an FXML. This method can be overridden if you need
     * to perform any procedure at controller startup.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
     * If you have your own stage you can pass here, Normally used for the
     * primary stage
     *
     * @param stage
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
     * Set blur effect on parent window
     *
     * @param blur
     * @return
     */
    public WindowControllerFx setBlur(boolean blur) {
        this.blur = blur;
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
     * Maximize stage. Note: can only called after window creation
     *
     * @return
     * @throws RuntimeException if called before window creation
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
     * @throws RuntimeException if called before window creation
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
     * @throws RuntimeException if called before window creation
     */
    public WindowControllerFx setWidth(double width) {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setWidth(width);
        return this;
    }

    /**
     * Minimize stage. Note: can only called after window creation
     *
     * @return
     * @throws RuntimeException if called before window creation
     */
    public WindowControllerFx setMinimized() {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setMaximized(false);
        return this;
    }

    /**
     * Switch to fullscreen mode. Note: can only called after window creation
     *
     * @return
     * @throws RuntimeException if called before window creation
     */
    public WindowControllerFx fullScreen() {
        if (stage == null) {
            throw new RuntimeException(ERROR_MSG);
        }
        stage.setFullScreen(true);
        return this;
    }

    /**
     * Exit fullscreen mode. Note: can only called after window creation
     *
     * @return
     * @throws RuntimeException if called before window creation
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
     * @return title
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

        // Load coded layout
        Region codedLayout = getRootPane();

        // Check if getFXML methdod was overwritten
        if (getFXML() != null && codedLayout == null) {

            // Controller object, refresent FXML defined controller
            Object controller;

            FXMLLoader loader = new FXMLLoader();

            // Define the location based on getFXML method
            loader.setLocation(getClass().getResource(getFXML()));

            try {

                /**
                 * Overrides the controller defined on FXML
                 */
                loader.setControllerFactory((c) -> this);

                // Load the root component from FXML
                rootWindowPane = loader.load();

                controller = loader.getController();

            } catch (Exception e) {
                // If occurs controller not defined exception, the controller is defined to null 
                controller = null;

                // If the exception is not related to controller definition throws again
                if (!e.getMessage().contains("specified")) {
                    throw e;
                }
            }

            // If the controller is null (controler not defined exception)
            if (controller == null) {

                // Is created another loader
                loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(getFXML()));

                loader.setController(this);
                rootWindowPane = loader.load();
            }

        } else if (codedLayout != null) {

            // If the layout is hardcoded
            rootWindowPane = codedLayout;

        } else {
            throw new RuntimeException("The content source is not defined, you must override getFXML or getRootPane!");
        }
    }

    /**
     * Show's the window on decorated mode
     *
     * @return
     */
    public WindowControllerFx show() {
        return show(parent);
    }

    /**
     * Show's the window on decorated mode
     *
     * @param parent parent window
     * @return
     */
    public WindowControllerFx show(Window parent) {
        try {
            this.parent = parent;
            loadView();

            if (stage == null) {
                stage = WindowControllerFxUtils.createStageFromContent(parent, rootWindowPane, title.get());
                stage.titleProperty().bind(title);
                stage.initModality(Modality.NONE);
                stage.initOwner(parent);
                stage.initStyle(StageStyle.DECORATED);

                if (icon != null) {
                    stage.getIcons().add(icon);
                }
            } else {
                stage.setScene(new Scene(rootWindowPane));
            }

            if (blur) {
                applyShowEffect();
            }

            if (wait) {
                stage.showingProperty().addListener((v, o, n) -> {
                    if (n) {
                        onShow();
                    }
                });
                stage.showAndWait();
            } else {
                stage.show();
            }

            onShow();

            return this;

        } catch (Exception ex) {
            throw new RuntimeException("Failed to load window", ex);
        }
    }

    /**
     * Show's the window on decorated mode and in modal
     *
     * @return
     */
    public WindowControllerFx showModal() {
        return showModal(parent);
    }

    /**
     * Show's the window on decorated mode and in modal
     *
     * @param parent
     * @return
     */
    public WindowControllerFx showModal(Window parent) {
        try {
            this.parent = parent;
            modality = modality != null ? modality : Modality.WINDOW_MODAL;
            System.out.println(modality);
            loadView();
            if (stage == null) {
                stage = WindowControllerFxUtils.createStageFromContent(parent, rootWindowPane, title.get());
                stage.titleProperty().bind(title);
                stage.initModality(modality);
                stage.initOwner(parent);
                stage.initStyle(StageStyle.DECORATED);

                if (icon != null) {
                    stage.getIcons().add(icon);
                }
            } else {
                stage.setScene(new Scene(rootWindowPane));
            }

            if (blur) {
                applyShowEffect();
            }
            if (wait) {
                stage.showingProperty().addListener((v, o, n) -> {
                    if (n) {
                        onShow();
                    }
                });
                stage.showAndWait();
            } else {
                stage.show();
            }

            onShow();
            return this;

        } catch (Exception ex) {
            throw new RuntimeException("Failed to load window", ex);
        }
    }

    /**
     * Show the window without decoration
     *
     * @param transparent
     * @return
     */
    public WindowControllerFx showUndecorated(@NamedArg(value = "transparent") boolean transparent) {
        return showUndecorated(null, transparent);
    }

    /**
     * Show the window without decoration
     *
     * @param parent
     * @param transparent transparent root pane
     * @return
     */
    public WindowControllerFx showUndecorated(@NamedArg(value = "parent") Window parent, @NamedArg(value = "transparent") boolean transparent) {
        try {
            this.parent = parent;
            modality = modality != null ? modality : Modality.WINDOW_MODAL;
            loadView();
            if (stage == null) {
                stage = WindowControllerFxUtils.createStageFromContent(parent, rootWindowPane, title.get());
                stage.titleProperty().bind(title);
                stage.initModality(modality);
                stage.initOwner(parent);
                stage.initStyle(StageStyle.UNDECORATED);

                if (icon != null) {
                    stage.getIcons().add(icon);
                }
            } else {
                stage.setScene(new Scene(rootWindowPane));
            }

            if (transparent) {
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.getScene().setFill(Color.TRANSPARENT);
                rootWindowPane.setStyle("-fx-background-color: transparent;");
            }

            if (blur) {
                applyShowEffect();
            }
            if (wait) {
                stage.showingProperty().addListener((v, o, n) -> {
                    if (n) {
                        onShow();
                    }
                });
                stage.showAndWait();
            } else {
                stage.show();
            }

            onShow();
            return this;

        } catch (Exception ex) {
            throw new RuntimeException("Failed to load window", ex);
        }
    }

    /**
     * Creates a modal dialog with close button
     *
     * @return
     */
    public WindowControllerFx showAsDialg() {
        return showAsDialg(null);
    }

    /**
     * Creates a modal dialog with close button
     *
     * @param parent parent window
     * @return
     */
    public WindowControllerFx showAsDialg(Window parent) {
        try {
            this.parent = parent;
            modality = modality != null ? modality : Modality.WINDOW_MODAL;
            loadView();

            if (stage == null) {
                stage = WindowControllerFxUtils.createStageFromContent(parent, rootWindowPane, title.get());
                stage.titleProperty().bind(title);
                stage.initModality(modality);
                stage.initOwner(parent);
                stage.initStyle(StageStyle.UTILITY);

                if (icon != null) {
                    stage.getIcons().add(icon);
                }
            } else {
                stage.setScene(new Scene(rootWindowPane));
            }

            if (blur) {
                applyShowEffect();
            }
            if (wait) {
                stage.showingProperty().addListener((v, o, n) -> {
                    if (n) {
                        onShow();
                    }
                });

                stage.showAndWait();
            } else {
                stage.show();
            }
            onShow();
            return this;

        } catch (Exception ex) {
            throw new RuntimeException("Failed to load window", ex);
        }
    }

    /**
     * Create and return decorated stage
     *
     * @return
     */
    public Stage createStage() {
        return createStage(null);
    }

    /**
     * Create and return decorated stage
     *
     * @param parent parent window
     * @return
     */
    public Stage createStage(Window parent) {
        try {
            this.parent = parent;
            modality = modality != null ? modality : Modality.NONE;
            loadView();

            if (stage == null) {

                stage = WindowControllerFxUtils.createStageFromContent(parent, rootWindowPane, title.get());
                stage.titleProperty().bind(title);
                stage.initModality(modality);
                stage.initOwner(parent);
                stage.initStyle(StageStyle.DECORATED);

                if (icon != null) {
                    stage.getIcons().add(icon);
                }
            } else {
                stage.setScene(new Scene(rootWindowPane));
            }

            stage.showingProperty().addListener((v, o, n) -> {
                if (n) {
                    onShow();
                }
            });

            if (blur) {
                applyShowEffect();
            }

            return stage;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load window", ex);
        }
    }

    /**
     * Get the scene window
     *
     * @return
     */
    public Stage getWindow() {
        return stage;
    }

    /**
     * On Show Handler, should overridden if want execute something on window
     * shown
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

//        final Effect EFFECT = new ColorAdjust(0, 0, -0.52, 0);
//
//        getWindow().setOnShown((evt) -> {
//            if (parent != null) {
//                oldEffectParent = parent.getScene().getRoot().getEffect();
//                parent.getScene().getRoot().setEffect(EFFECT);
//            }
//        });
//
//        getWindow().setOnHiding((event) -> {
//            if (parent != null) {
//                parent.getScene().getRoot().setEffect(oldEffectParent);
//            }
//        });
    }

    /**
     * Use the WindowControllerFxUtils instead
     *
     * @param parent
     * @param content
     * @param titulo
     * @param style
     * @param modal
     * @return
     * @deprecated
     */
    @Deprecated
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
