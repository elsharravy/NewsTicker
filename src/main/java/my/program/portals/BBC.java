package my.program.portals;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import my.program.News;
import my.program.NewsPortal;

public class BBC extends NewsPortal {

    static private final String NEWS_XPATH = "//a[@class='gs-c-promo-heading gs-o-faux-block-link__overlay-link gel-pica-bold nw-o-link-split__anchor']";

    public BBC(String url) {
        super(url , 13);
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
        HtmlAnchor anchor = (HtmlAnchor) element;
        if( anchor != null )
        {
            String link = "https://bbc.com" + anchor.getHrefAttribute();
  //          String title = anchor.asText();
            String title = anchor.getTextContent();
            News news = new News( title, link);
            //////////////////////
            System.out.println("News: " + news);
            /////////////////////
            return news;
        }
        else { throw new NullPointerException(url + " Article null exception"); }
    }


}
