package sample.windows;

import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ConfigurationFile;
import sample.Controller;
import sample.NewsTicker;

public class MainWindow {

    final public static double WINDOW_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    final public static double WINDOW_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight() / 8;
    final public static String TITLE = "News Ticker";

    private OptionsWindow optionsWindow;

    private Controller controller;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    private Stage stage;

    public MainWindow(Stage stage , OptionsWindow optionsWindow, ConfigurationFile cf, NewsTicker newsTicker) throws Exception
    {
        this.optionsWindow = optionsWindow;
        this.stage = stage;
        initializeWindow( cf , newsTicker);
        initializeStage();
    }

    private void initializeStage( )
    {
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.setX(0);
        stage.setY(0);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    private void initializeWindow( ConfigurationFile cf ,  NewsTicker newsTicker) throws Exception
    {
         loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
         root = loader.load();
         controller = loader.getController();
         scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
         scene.getStylesheets().add("style.css");
        //scene.setFill(Color.TRANSPARENT);
        controller.setOptionsWindow(optionsWindow);
        controller.setCf(cf);
        controller.setTicker( newsTicker );
        initializeAccelerators();
    }

    public void initializeAccelerators()
    {
        ObservableMap<KeyCombination, Runnable> observators = scene.getAccelerators();

        buttonAcceleratorRunnable leftButtonAccelerator = new buttonAcceleratorRunnable( controller.leftButton );
        buttonAcceleratorRunnable rightButtonAccelerator = new buttonAcceleratorRunnable( controller.rightButton );
        buttonAcceleratorRunnable optionsButtonAccelerator = new buttonAcceleratorRunnable( controller.optionsButton );
        buttonAcceleratorRunnable exitButtonAccelerator = new buttonAcceleratorRunnable( controller.exitButton );
        buttonAcceleratorRunnable leftPortalAccelerator = new buttonAcceleratorRunnable( controller.leftPortalButton );
        buttonAcceleratorRunnable rightPortalAccelerator = new buttonAcceleratorRunnable( controller.rightPortalButton );

        observators.put( new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_ANY), leftButtonAccelerator);
        observators.put( new KeyCodeCombination(KeyCode.LEFT, KeyCombination.SHORTCUT_ANY), leftButtonAccelerator);
        observators.put( new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.SHORTCUT_ANY), rightButtonAccelerator);
        observators.put( new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_ANY), rightButtonAccelerator);

        observators.put( new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN), leftPortalAccelerator);
        observators.put( new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN), rightPortalAccelerator);
        observators.put( new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN), leftPortalAccelerator);
        observators.put( new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.ALT_DOWN), rightPortalAccelerator);

        observators.put( new KeyCodeCombination(KeyCode.ESCAPE, KeyCombination.SHORTCUT_ANY), exitButtonAccelerator);
        observators.put( new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_ANY), optionsButtonAccelerator);
    }

    public Controller getController() {
        return controller;
    }
}

class buttonAcceleratorRunnable implements Runnable
{
Button button;

    buttonAcceleratorRunnable( Button button )
    {
        this.button = button;
    }

    @Override
    public void run() {
        button.fire();
    }
}
