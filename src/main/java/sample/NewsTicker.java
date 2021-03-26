package sample;

import my.program.News;
import my.program.NewsFeed;
import my.program.NewsPortal;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class NewsTicker {

    private NewsFeed feed;
    private Thread thread;
    private NewsSwitcher newsSwitcher;

    private CycleIterator<NewsPortal> displayedPortalIt;
    private LeftRightIterator<News> displayedNewsIt;
    private News displayedNews;
    private NewsPortal displayedPortal;

    public NewsTicker( ) {

   //     startNewsSwitcher( controller , 5000 );
    }

    public void initializeNewsTicker( Controller controller ,ConfigurationFile cf )
    {
        initializeNewsFeed(controller , cf);
        initializeIterators();
    }

    public void switchRightPortal()
    {
    displayedPortal = displayedPortalIt.next();
    displayedNewsIt = new LeftRightIterator<News>( displayedPortal.getNews() );
    displayedNews = displayedNewsIt.next();
    }

    public void switchLeftPortal() {
        displayedPortal = displayedPortalIt.previous();
        displayedNewsIt = new LeftRightIterator<News>(displayedPortal.getNews());
        displayedNews = displayedNewsIt.next();
    }

    public void switchLeftPortalEnding() {
        displayedPortal = displayedPortalIt.previous();
        ArrayList<News> news = displayedPortal.getNews();
        displayedNewsIt = new LeftRightIterator<News>( news , news.size() );
    }

    private void changeRightNews()
    {
        try
        {
              displayedNews = displayedNewsIt.next();
        }
        catch( NoSuchElementException e)
        {
            displayedPortal = displayedPortalIt.next();
            displayedNewsIt = new LeftRightIterator<News>( displayedPortal.getNews() );
 //            switchRightPortal();
             changeRightNews();
        }
    }

    private void changeLeftNews()
    {
        try
        {
            displayedNews = displayedNewsIt.previous();
        }
        catch( NoSuchElementException e)
        {
            displayedPortal = displayedPortalIt.previous();
            ArrayList<News> news = displayedPortal.getNews();
            displayedNewsIt = new LeftRightIterator<News>( news , news.size() );
 //           switchLeftPortalEnding();
            changeLeftNews();
        }
    }

    public void switchLeftNews()
    {
        changeLeftNews();
    }

    public void switchRightNews()
    {
        changeRightNews();
    }

    private void initializeNewsFeed( Controller controller , ConfigurationFile cf )
    {
        ArrayList<NewsPortal> portals = new ArrayList<NewsPortal>();
        Map<String, Integer> configuration = cf.getConfiguration();

        for( String portalName : NewsPortal.NEWS_PORTALS ) {
            if (configuration.getOrDefault(portalName, 0) == 1) {
                portals.add( NewsPortal.FactoryMethod(portalName) );
            }
        }

        feed = new NewsFeed( portals );

        feed.getLoadedPortalsNotifier().addObserver( controller.getPortalsLoadedObserver() );

        feed.loadNewses();

        startNewsSwitcher( controller , configuration.get("Interval") );
    }

    private void initializeIterators()      // first we need to initialize NewsFeed
    {
        displayedPortalIt = new CycleIterator<NewsPortal>( feed.getPortals() );
        displayedPortal = displayedPortalIt.next();
     // displayedNewsIt = new LeftRightIterator<News>( displayedPortal.getNews() );
        displayedNewsIt = new LeftRightIterator<News>( new ArrayList<News>(0) );
    }

    public News getDisplayedNews() {
        return displayedNews;
    }

    public NewsPortal getDisplayedPortal() {
        return displayedPortal;
    }

    public void startNewsSwitcher( Controller controller, long interval )
    {
        resetNewsSwitcher();
        newsSwitcher = new NewsSwitcher( controller, interval );
        thread = new Thread( newsSwitcher );
        thread.start();
    }

    public void resetNewsSwitcher()
    {
        if( thread != null ) {
            thread.interrupt();
            thread = new Thread( newsSwitcher );
            thread.start();
        }
    }

    public NewsFeed getFeed() {
        return feed;
    }
}
