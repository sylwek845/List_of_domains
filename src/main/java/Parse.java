import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by : Sylwester Zalewski
 * Date: 08/08/2017
 * <p>
 * This class use...
 */
public class Parse {
    private List<DomainContainer> domains;
    private URI uri;

    public Parse() {
        domains = new ArrayList<DomainContainer>();
    }
//TODO - wrong domain handler
    public Document getHtml(String URL) throws TimeoutException, IOException {
        Document doc = Jsoup.connect(URL).get();
        return doc;
    }

    public List<DomainContainer> ParseHTML(Document document) {
        HashMap<String, Integer> host_position = new HashMap<String, Integer>();
        Elements elements = document.select("a[href]");

        for (int i = 0; i < elements.size(); i++) {
            String absHref = elements.get(i).attr("abs:href");
            try {
                uri = new URI(absHref);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String hostname = uri.getHost().replace("www.","");
            if (hostname != null)
                if (host_position.containsKey(hostname)) {
                    this.domains.get(host_position.get(hostname)).incrementDomainCounter();
                } else {
                    DomainContainer domainContainer = new DomainContainer(hostname);
                    this.domains.add(domainContainer);
                    host_position.put(hostname, this.domains.size() - 1);
                }
        }
        return this.domains;
    }

    public List<DomainContainer> getDomains() {
        return domains;
    }
}
