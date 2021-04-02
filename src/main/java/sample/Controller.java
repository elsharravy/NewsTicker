package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import my.program.NewsFeed;
import sample.windows.MainWindow;
import sample.windows.OptionsWindow;

import java.util.Observable;
import java.util.Observer;

public class Controller  {

    PortalsLoadedObserver portalsLoadedObserver;

    OptionsWindow optionsWindow;
    ConfigurationFile cf;
    NewsTicker ticker;

    @FXML
    GridPane gridPane;
    @FXML
       public Button leftButton, rightButton, exitButton, optionsButton, leftPortalButton, rightPortalButton;
    @FXML
        Label titleLabel;
    @FXML
        Label portalNameLabel;
    @FXML
    ProgressIndicator newsLoadingIndicator;



    @FXML
    public void initialize(){
        portalsLoadedObserver = new PortalsLoadedObserver();
        newsLoadingIndicator.setProgress(-1);
       // gridPane.setBackground( new Background(new BackgroundFill( Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY )) );
//        newsLoadingIndicator.setProgress(0.5);
    }

    @FXML
    private void exitButtonClicked()
    {
       Control.ApplicationExit(0);
    }

    @FXML
    private void leftButtonClicked(ActionEvent event)
    {
   //     System.out.println("Left Button Clicked");
        try {
            switchLeftNews();
            ticker.resetNewsSwitcher();
        }
        catch (NullPointerException e)
        {
            System.out.println("There is no news, probably they are loading");
        }
  //      System.out.println( ticker.getDisplayedNews());
    }

    @FXML
    private void rightButtonClicked(ActionEvent event)
    {
   //     System.out.println("Right Button Clicked");
try {
    switchRightNews();
    ticker.resetNewsSwitcher();
}
               catch (NullPointerException e)
        {
            System.out.println("There is no news, probably they are loading");
        }
 //       System.out.println( ticker.getDisplayedNews());
    }

    @FXML
    private void leftPortalButtonClicked(ActionEvent event)
    {
        try {
            switchLeftPortal();
            ticker.resetNewsSwitcher();
        }
        catch (NullPointerException e)
        {
            System.out.println("There are no portals, probably they are loading");
        }
    }

    @FXML
    private void rightPortalButtonClicked(ActionEvent event)
    {
        try {
            switchRightPortal();
            ticker.resetNewsSwitcher();
        }
        catch (NullPointerException e)
        {
            System.out.println("There are no portals, probably they are loading");
        }
    }

    @FXML
    private void titleLabelClicked(MouseEvent event)
    {
        String url = ticker.getDisplayedNews().getLink();
        try
        {
            InternetBrowse.openUrl( url );
        }
        catch (Exception e)
        {
            System.out.println("Problem while trying to open: " + url);
        }
    }

    public void switchRightNews()
    {
     //   try {
            ticker.switchRightNews();
            setNews(ticker);
   //     }
//        catch (NullPointerException e)
//        {
//            System.out.println("There is no news, probably they are loading");
//        }
        }

    public void switchLeftNews()
    {
       // try {
            ticker.switchLeftNews();
            setNews(ticker);
       // }
//        catch (NullPointerException e)
//        {
//            System.out.println("There is no news, probably they are loading");
//        }
    }

    public void switchRightPortal()
    {
        ticker.switchRightPortal();
        setNews(ticker);
    }

    public void switchLeftPortal()
    {
        ticker.switchLeftPortal();
        setNews(ticker);
    }

    private void setNewsTitle( String title )
    {
        titleLabel.setText(title);
    }

    private void setPortalName( String portalName )
    {
        portalNameLabel.setText(portalName);
    }

    private void setNews ( NewsTicker ticker )
    {
        setNewsTitle( ticker.getDisplayedNews().getTitle() );
        setPortalName( ticker.getDisplayedPortal().getUrl() );
    }

    public void setTicker(NewsTicker ticker) {
        this.ticker = ticker;
    }

    public void initializeTicker(NewsTicker ticker)
    {
        setTicker( ticker );
        //switchRightNews();
    }

    @FXML
    void optionsButtonClicked( ActionEvent event )
    {
        Stage optionsStage = optionsWindow.getStage();

        if( optionsStage.isShowing() )
        {
            optionsStage.hide();
        }
        else
        {
            optionsStage.setX(0);
            optionsStage.setY(MainWindow.WINDOW_HEIGHT);
            optionsStage.show();
        }

    }

    public void setOptionsWindow(OptionsWindow optionsWindow) {
        this.optionsWindow = optionsWindow;
    }

    public void setCf(ConfigurationFile cf) {
        this.cf = cf;
    }

    class PortalsLoadedObserver implements Observer
    {

        @Override
        public void update(Observable o, Object arg) {
            System.out.println("UPDATE: " + arg.toString());
            Integer num = (Integer) arg;
//            System.out.println(ticker.hashCode());
            newsLoadingIndicator.setProgress( ( num.floatValue()  / ( (NewsFeed.LoadedPortalsNotifier)o).getPortalsQuantity() ) );
        }
    }

    public PortalsLoadedObserver getPortalsLoadedObserver() {
        return portalsLoadedObserver;
    }
}
