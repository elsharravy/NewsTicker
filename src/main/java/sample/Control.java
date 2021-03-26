package sample;

import javafx.scene.control.Alert;

public class Control {

    public static void ApplicationExit( int status )
    {
        System.exit(status);
    }

    public static void ShowErrorDialog(Alert.AlertType type, String title, String text)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
