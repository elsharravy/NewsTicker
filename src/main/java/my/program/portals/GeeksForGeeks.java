package my.program.portals;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import my.program.News;
import my.program.NewsPortal;

public class GeeksForGeeks extends NewsPortal {

    static private final String NEWS_XPATH = "//div[@class='head']";

    public GeeksForGeeks(String url) {
        super(url , 15);
    }

    public void initializeNews( )
    {
        System.out.println("///////////////////////////////////////////////" + url + " ; Initialization started");
        initializeWebPage( url );

        addNews( getNewsList(NEWS_XPATH) );

        System.out.println("Initialization succesfull: " + url);
    }

    @Override
    protected News retrieveNews(HtmlElement element )
    {
        HtmlAnchor anchor = element.getFirstByXPath(".//a");
        if( anchor != null )
        {
            String link = anchor.getHrefAttribute();
            String title = anchor.asText();
            News news = new News( title, link);
            //////////////////////
            System.out.println("News: " + news);
            /////////////////////
            return news;
        }
        else { throw new NullPointerException(url + " Article null exception"); }
    }



}
