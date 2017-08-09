
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


/**
 * Created by : Sylwester Zalewski
 * Date: 08/08/2017
 */

public class Main {
    public static void main (String args [])
    {

        Scanner scanner = new Scanner(System.in);
        String url = "https://stackoverow.com/questions/9607903/get-domain-name-from-given-url";
        List<DomainContainer> domains = null;
        Parse parse = new Parse();

        Document document =null;

        try {
            document = parse.getHtml(url);
        } catch (IOException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }

        if(document != null)
             domains = parse.ParseHTML(document);
            //for(int i = 0; i < parse.ParseHTML(document).size();i++)
            for(int i = 0; i < domains.size();i++)
            {
                System.out.print(parse.getDomains().get(i).getDomainHost()+ " - " + parse.getDomains().get(i).getDomainCount() + "\n");
            }

        System.out.print("Enter... ");


        int test = scanner.nextInt();
    }
}
