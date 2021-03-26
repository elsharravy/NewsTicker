package sample.windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import my.program.NewsPortal;
import sample.ConfigurationFile;
import sample.NewsTicker;

import java.util.Map;

public class OptionsController  {

    final static int DEFAULT_INTERVAL = 8000;
    final static String TOGGLE_BUTTON_SELECTED = "SHOW";
    final static String TOGGLE_BUTTON_NOT_SELECTED = "DON`T SHOW";

   ToggleButton[] toggleButtons = new ToggleButton[ NewsPortal.NEWS_PORTALS.length ] ;

NewsTicker ticker;
ConfigurationFile cf;
OptionsWindow optionsWindow;
MainWindow mainWindow;

    @FXML
 TextField intervalTextField;

    @FXML
    Button saveButton;

    @FXML
    ToggleButton onetToggle, howToGeekToggle, bullDogJobToggle, hackerNewsToggle, theCrazyProgrammerToggle, betterProgrammingToggle, hackrToggle;


    @FXML
    private void initialize()
    {
        initializeArrayOfToggleButtons();
    }

    @FXML
    private void onSaveButtonClicked( MouseEvent e)
    {
        optionsWindow.getStage().hide();
        cf.loadKeyValue( "Interval" , Integer.valueOf(intervalTextField.getText()) * 1000 );
        String[] portals = NewsPortal.NEWS_PORTALS;
        for(int i = 0 ; i < portals.length ; ++i )
        {
            cf.loadKeyValue( portals[i] , toggleButtons[i].isSelected() );
        }
        cf.saveSettingsToFile();
        ticker.initializeNewsTicker( mainWindow.getController() , cf );
    }

    public void initializeFields( ConfigurationFile cf )
    {
        initializeInterval(cf);
        initializeButtons(cf);
    }

    private void initializeArrayOfToggleButtons()
    {
       toggleButtons[0] = onetToggle;
       toggleButtons[1] = howToGeekToggle;
       toggleButtons[2] = bullDogJobToggle;
       toggleButtons[3] = hackerNewsToggle;
       toggleButtons[4] = theCrazyProgrammerToggle;
       toggleButtons[5] = betterProgrammingToggle;
       toggleButtons[6] = hackrToggle;
    }

    private void initializeInterval(ConfigurationFile cf)
    {
        Integer value = cf.getConfiguration().get("Interval");
        if( value != null )
        {
            setInterval( value / 1000 );
        }
        else
        {
            setInterval( DEFAULT_INTERVAL );
        }
    }

    private void initializeButtons( ConfigurationFile cf )
    {
        Map<String, Integer> configuration = cf.getConfiguration();
        Integer value;

        for( int i = 0 ; i < NewsPortal.NEWS_PORTALS.length ; ++i)
        {
            value = configuration.getOrDefault( NewsPortal.NEWS_PORTALS[i] , 0 );
            if  ( value <= 0 )
            {
                toggleButton( toggleButtons[i] , false );
            }
            else
            {
                toggleButton( toggleButtons[i] , true );
            }
        }
    }

    private void toggleButton(ToggleButton button, Boolean selected)
    {
        button.setSelected(selected);
        if( selected )
        {
        button.setText(TOGGLE_BUTTON_SELECTED);
        }
        else
        {
        button.setText(TOGGLE_BUTTON_NOT_SELECTED);
        }
    }

    public int getInterval() {
        return Integer.parseInt(intervalTextField.getText());
    }

    public void setInterval( Integer interval ) {
        this.intervalTextField.setText( interval.toString() );
    }

    @FXML
    private void toggled(MouseEvent e)
    {
        ToggleButton button = (ToggleButton) e.getSource();
        toggleButton( button , button.isSelected() );
    }

    public void setCf(ConfigurationFile cf) {
        this.cf = cf;
    }

    public void setOptionsWindow(OptionsWindow optionsWindow) {
        this.optionsWindow = optionsWindow;
    }

    public void setTicker(NewsTicker ticker) {
        this.ticker = ticker;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
}
