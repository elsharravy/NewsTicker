package my.program;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import sample.Control;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for portals from which you want to read news
 */

public abstract class NewsPortal {

    static public final String[] NEWS_PORTALS = {"Onet" , "HowToGeek", "BullDogJob", "HackerNews", "TheCrazyProgrammer", "BetterProgramming", "Hackr"};

    static public final String[] PORTAL_LINKS = {"https://www.onet.pl/" , "https://www.howtogeek.com/", "https://bulldogjob.pl/blog","https://news.ycombinator.com/","https://www.thecrazyprogrammer.com/"
    ,"https://medium.com/better-programming","https://hackr.io/blog" };



    static final String INVALID_NEWS_PORTAL_NAME_MESSAGE = "Invalid Portal Name: ";

    protected final int maxNews;
    protected WebClient client = new WebClient();
    protected HtmlPage page;
    protected String url;
    protected ArrayList<News> news;

    static public NewsPortal FactoryMethod(String typeName)
    {
        if(typeName.equalsIgnoreCase( NEWS_PORTALS[0] ))  { return new Onet( PORTAL_LINKS[0] ); }
        if(typeName.equalsIgnoreCase( NEWS_PORTALS[1]))  { return new HowToGeek( PORTAL_LINKS[1] ); }
        if(typeName.equalsIgnoreCase( NEWS_PORTALS[2]))  { return new BullDogJob( PORTAL_LINKS[2] ); }
        if(typeName.equalsIgnoreCase( NEWS_PORTALS[3]))  { return new HackerNews( PORTAL_LINKS[3] ); }
        if(typeName.equalsIgnoreCase( NEWS_PORTALS[4]))  { return new TheCrazyProgrammer( PORTAL_LINKS[4] ); }
        if(typeName.equalsIgnoreCase( NEWS_PORTALS[5]))  { return new BetterProgramming( PORTAL_LINKS[5] ); }
        if(typeName.equalsIgnoreCase( NEWS_PORTALS[6]))  { return new Hackr( PORTAL_LINKS[6] ); }
        else { throw new InvalidNewsPortalNameException( INVALID_NEWS_PORTAL_NAME_MESSAGE + typeName ); }
    }

    protected NewsPortal(String url , int maxNews) {
        this.url = url;
        this.maxNews = maxNews;
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        /////////////
        System.out.println("NewsPortal Constructor: "+url+" ; MaxNews: "+maxNews);
    }
    public void displayNews()
    {
        System.out.println("News from portal: " + url);
        for( News news : news)
        {
            System.out.println(news);
        }
        System.out.println();
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public String getUrl() {
        return url;
    }

    protected void initializeWebPage( String url)
    {
        try {
            page = client.getPage(url);
        }
        catch ( Exception e)
        {
            System.out.println("Error while loading page: " + url);
            Platform.runLater(
                    new Runnable() {
                        @Override
                        public void run() {
                            Control.ShowErrorDialog( Alert.AlertType.ERROR, "Internet Connection Problem" , "Error while loading page: " + url );
                            Control.ApplicationExit(-1);
                        }
                    }

            );

        }
    }

    protected List<HtmlElement> getNewsList(String xPath )
    {
        return page.getByXPath(xPath);
    }

    protected void addNews( List<HtmlElement> elements )
    {
        int newsNumber = Integer.min( maxNews , elements.size() );
        news = new ArrayList<News>( newsNumber );
        /////////////
        System.out.println( "Number of newses: " + newsNumber );
        /////////////
        for( int i = 0 ; i < newsNumber ; ++i )
        {
            try{
                news.add( retrieveNews(elements.get(i)) );
            }
        catch (NullPointerException e)
        {
            System.out.println("News is null in: "+url);
        }
        }
    }


    protected abstract void initializeNews();
    protected abstract News retrieveNews( HtmlElement element );


    static class InvalidNewsPortalNameException extends IllegalArgumentException
    {
        InvalidNewsPortalNameException(String msg)
        {
            super(msg);
        }
    }

}

