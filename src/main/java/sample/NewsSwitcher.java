package sample;

import javafx.application.Platform;

public class NewsSwitcher implements Runnable {

    Controller controller;
    long interval;

    NewsSwitcher( Controller controller , long interval )
    {
        this.controller = controller;
        this.interval = interval;
    }

    @Override
    public void run() {
     //   System.out.println("News Switcher Started");
        while( true )
        {
            try {
                Thread.sleep( interval );
                System.out.println("News Switcher switch next");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            controller.switchRightNews();
                        }
                        catch(NullPointerException e )
                        {
                            System.out.println("There is no more news, probably they are loading");
                        }
                    }
                });

            } catch (InterruptedException e) {
               // System.out.println("News Switcher Interrupted");
                return;
            }
        }
    }
}
