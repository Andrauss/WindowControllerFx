package br.com.fandrauss.fx.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author andrauss
 */
public class WindowControllerFxUtils {

    /**
     * Utility method to create stages
     *
     * @param parent window title
     * @param content window content
     * @param title window title
     * @return
     * @see Stage
     */
    public static Stage createStageFromContent(Window parent, Parent content, String title) {
        return createStageFromContent(parent, content, title, StageStyle.UTILITY, Modality.WINDOW_MODAL);
    }

    /**
     * Utility method to create stages
     *
     * @param parent window title
     * @param content window content
     * @param title window title
     * @param style window style
     * @param modal window modality
     * @return
     * @see Stage
     * @see StageStyle
     * @see Modality
     */
    public static Stage createStageFromContent(Window parent, Parent content, String title, StageStyle style, Modality modal) {
        Stage s = new Stage(style);

        Scene scene = new Scene(content);
        s.setScene(scene);
        s.initOwner(parent);
        s.initModality(modal);

        s.setTitle(title);

        return s;
    }

}
