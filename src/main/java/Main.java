
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


/**
 * Created by : Sylwester Zalewski
 * Date: 08/08/2017
 */

public class Main {
    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        String url = "http://www.codejava.net/java-se/swing/jlist-basic-tutorial-and-examples";
        DefaultListModel<DomainContainer> domains = null;
        Parse parse = new Parse();
        Document document = null;
        try {
            document = parse.getHtml(url);
        } catch (IllegalArgumentException e) {
            //  e.printStackTrace();
        } catch (IOException e) {
            //  e.printStackTrace();
        } catch (TimeoutException e) {
            //  e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (document != null) {
            domains = parse.ParseHTML(document);
            //for(int i = 0; i < parse.ParseHTML(document).size();i++)
            for (int i = 0; i < domains.size(); i++) {
                System.out.print(parse.getDomains().get(i).getDomainHost() + " - " + parse.getDomains().get(i).getDomainCount() + "\n");
            }
        } else
            System.out.print("HTML document empty, check your url and internet connection");

        System.out.print("Enter... ");

        //UrlForms urlForms = new UrlForms();



        int test = scanner.nextInt();
    }
}
