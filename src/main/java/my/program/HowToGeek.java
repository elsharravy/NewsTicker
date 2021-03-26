package my.program;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;

public class HowToGeek extends NewsPortal {

    static private final String NEWS_XPATH = "//article";

     HowToGeek(String url) {
        super(url , 11);
    }

    public void initializeNews( )
    {
        System.out.println("///////////////////////////////////////////////" + url + " ; Initialization started");
        initializeWebPage( url );

        addNews( getNewsList(NEWS_XPATH) );

        System.out.println("Initialization succesfull: " + url);
    }

    @Override
    protected News retrieveNews( HtmlElement element )
    {
        HtmlAnchor anchor = element.getFirstByXPath(".//h2[@class='entry-title']/a");
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
