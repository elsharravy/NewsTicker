package sample.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ConfigurationFile;
import sample.NewsTicker;

import java.io.IOException;

public class OptionsWindow {

    final public static int WINDOW_WIDTH = 250;
    final public static int WINDOW_HEIGHT = 350;
    final public static String TITLE = "Options";


    private FXMLLoader loader;
    private OptionsController controller;
    private Parent root;
    private Scene scene;
    private Stage stage;

    public OptionsWindow( ConfigurationFile cf ,  NewsTicker newsTicker) throws IOException
    {
        initializeWindow( cf , newsTicker);
        initializeStage();
    }

    private void initializeStage( )
    {
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        //    primaryStage.initStyle(StageStyle.TRANSPARENT);
        //    primaryStage.show();
    }

    private void initializeWindow(ConfigurationFile cf,  NewsTicker newsTicker) throws IOException
    {
        loader = new FXMLLoader(getClass().getResource("/options.fxml"));
        root = loader.load();
        controller = loader.getController( );
        controller.setCf(cf);
        controller.initializeFields( cf );
        controller.intializeGridPane();
        controller.setOptionsWindow(this);
        controller.setTicker( newsTicker );
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add("style.css");
        stage = new Stage( );
    }

    public Stage getStage() {
        return stage;
    }

    public OptionsController getController() {
        return controller;
    }
}
