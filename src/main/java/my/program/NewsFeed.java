package my.program;

import java.util.ArrayList;
import java.util.Observable;

public class NewsFeed {

    private LoadedPortalsNotifier loadedPortalsNotifier;
    private ArrayList<NewsPortal> portals;
    int loadedPortals = 0;

    public NewsFeed( ArrayList<NewsPortal> portals ) {

        this.portals = portals;
        loadedPortalsNotifier = new LoadedPortalsNotifier();

    }

    public void loadNewses()
    {
        setLoadedPortals(0);

        PortalInitialization portalInit;
        Thread portalInitThread;

        for(int i = 0; i < portals.size() ; ++i)
        {
            portalInit = new PortalInitialization( portals.get(i), this);
            portalInitThread = new Thread( portalInit );
            portalInitThread.start();           // for rest of portals we initialize them in other threads
        }
    }

    public ArrayList<NewsPortal> getPortals() {
        return portals;
    }

    public int getPortalsQuantity()
    {
        return portals.size();
    }

    public int getLoadedPortals() {
        return loadedPortals;
    }

    public void setLoadedPortals(int loadedPortals) {
        this.loadedPortals = loadedPortals;
        loadedPortalsNotifier.notify( loadedPortals );
    }

    public class LoadedPortalsNotifier extends Observable
    {
         void notify(int loadedPortals) {
             setChanged();
             notifyObservers( loadedPortals );
         }

         public int getPortalsQuantity()
         {
             return portals.size();
         }

    }

    public LoadedPortalsNotifier getLoadedPortalsNotifier() {
        return loadedPortalsNotifier;
    }
}

 class PortalInitialization implements Runnable
{
    private NewsPortal portalToInitialize;
    private NewsFeed newsFeed;

    PortalInitialization( NewsPortal portalToInitialize, NewsFeed newsFeed )
    {
this.portalToInitialize = portalToInitialize;
this.newsFeed = newsFeed;
    }

    @Override
    public void run() {
        portalToInitialize.initializeNews();
        synchronized (newsFeed)
        {
            newsFeed.setLoadedPortals( newsFeed.getLoadedPortals() + 1 );
        }
    }
}