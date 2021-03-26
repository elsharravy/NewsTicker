package my.program;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

public class TheCrazyProgrammer extends NewsPortal {

    static private final String NEWS_XPATH = "//a[@rel='bookmark']";

    TheCrazyProgrammer( String url )
    {
        super( url, 10 );
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
        String link = element.getAttribute("href");
        String title = element.asText() ;
        News news = new News( title, link);
        //////////////////////
        System.out.println("News: " + news);
        /////////////////////
        return news;
    }


}
