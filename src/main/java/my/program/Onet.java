package my.program;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class Onet extends NewsPortal {

    static private final String NEWS_XPATH = "//a[@data-art-type='article']";

     Onet(String url) {
        super(url , 11);
    }

    public void initializeNews( )
    {
        System.out.println("///////////////////////////////////////////////" + url + " ; Initialization started");
        initializeWebPage( url );

        addNews( getNewsList(NEWS_XPATH) );

        System.out.println("///////////////////////////////////////////////Initialization succesfull: " + url);
    }

    @Override
    protected News retrieveNews( HtmlElement element )
    {
        HtmlAnchor anchor = (HtmlAnchor) element;
        String link = anchor.getHrefAttribute();
        String title = ((HtmlSpan) anchor.getFirstByXPath(".//span[@class='title']")).asText();
        News news = new News( title, link);
        //////////////////////
        System.out.println("News: " + news);
        /////////////////////
        return news;
    }

}
