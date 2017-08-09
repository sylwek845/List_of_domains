import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
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
    //private List<DomainContainer> domains;
    private DefaultListModel<DomainContainer> domains;
    private URI uri;

    public Parse() {
        //domains = new ArrayList<DomainContainer>();
        domains = new DefaultListModel<>();
    }
public Document getHtml(String URL) throws IOException, IllegalArgumentException, TimeoutException {

    return Jsoup.connect(URL).get();
    }

    public DefaultListModel<DomainContainer> ParseHTML(Document document) {
        HashMap<String, Integer> host_position = new HashMap<String, Integer>();
        Elements elements = document.select("a[href]");

        for (int i = 0; i < elements.size(); i++) {
            String absHref = elements.get(i).attr("abs:href");
            try {
                uri = new URI(absHref);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String hostname = null;
            try {
                hostname = uri.getHost().replace("www.", "");
            } catch (Exception e) {
            }

            if (hostname != null)
                if (host_position.containsKey(hostname)) {
                    this.domains.get(host_position.get(hostname)).incrementDomainCounter();
                } else {
                    DomainContainer domainContainer = new DomainContainer(hostname);
                    this.domains.addElement(domainContainer);
                    host_position.put(hostname, this.domains.size() - 1);
                }
        }
        return this.domains;
    }

    public DefaultListModel<DomainContainer> getDomains() {
        return domains;
    }
}
