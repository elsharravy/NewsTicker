package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.windows.MainWindow;
import sample.windows.OptionsWindow;

public class Main extends Application {

    final static String PATH_RELATIVE_FROM_LOCAL_APP_DATA = "\\NewsTicker\\NewsTicker.ini";
    final static String CONFIGURATION_FILE_PATH = System.getenv( "LOCALAPPDATA" ) + PATH_RELATIVE_FROM_LOCAL_APP_DATA ;

    NewsTicker newsTicker;
    ConfigurationFile cf;
    MainWindow mainWindow;
    OptionsWindow optionsWindow;

    @Override
    public void start(Stage primaryStage) throws Exception{

        cf = new ConfigurationFile( CONFIGURATION_FILE_PATH );
        cf.loadSettingsFromFile();

        newsTicker = new NewsTicker(  );

        optionsWindow = new OptionsWindow( cf, newsTicker );
        mainWindow = new MainWindow( primaryStage, optionsWindow , cf, newsTicker);

        optionsWindow.getController().setMainWindow( mainWindow );

        newsTicker.initializeNewsTicker(mainWindow.getController() , cf);
   }


    public static void main(String[] args) {
        launch(args);
    }


}


