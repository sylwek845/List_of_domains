/**
 * Created by : Sylwester Zalewski
 * Date: 08/08/2017
 *
 * This class use...
 */
public class DomainContainer {
    private String domainHost;
    private int domainCount;


    public DomainContainer(String domainHost)
    {
        this.domainHost = domainHost;
        this.domainCount = 1;
    }

    public String getDomainHost() {
        return domainHost;
    }

    public int getDomainCount() {
        return domainCount;
    }

    public void incrementDomainCounter() {
        this.domainCount++;
    }


    @Override
    public String toString() {
        return getDomainHost() + " - " + getDomainCount();
    }






}
