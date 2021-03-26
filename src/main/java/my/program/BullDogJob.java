package my.program;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

public class BullDogJob extends NewsPortal {

    static private final String NEWS_XPATH = "//div[@class='col-md-4 col-sm-6']";

    BullDogJob( String url )
    {
        super( url, 24 );
    }

    @Override
    protected void initializeNews() {
        System.out.println("///////////////////////////////////////////////" + url + " ; Initialization started");
        initializeWebPage( url );

        addNews( getNewsList( NEWS_XPATH ) );

        System.out.println("Initialization succesfull: " + url);
    }

    @Override
    protected News retrieveNews( HtmlElement element )
    {
        String link = element.getAttribute("data-item-url");;
        String title = ((HtmlElement) element.getFirstByXPath(".//div[@class='top']") ).asText();
        News news = new News( title, link);
        //////////////////////
        System.out.println("News: " + news);
        /////////////////////
        return news;
    }


}
