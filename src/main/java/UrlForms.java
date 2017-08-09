import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class UrlForms extends JFrame {
    private JTextField txt_url;
    private JPanel mainPanel;
    private JButton btn_DomainsButton;
    private JList lst_domains;

    public UrlForms() {
        btn_DomainsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String url = "https://www.jetbrains.com/help/idea/creating-and-opening-forms.html";
                DefaultListModel<DomainContainer> domains = null;
                Parse parse = new Parse();
                Document document = null;
                try {
                    document = parse.getHtml(url);
                } catch (IllegalArgumentException a) {
                    //  e.printStackTrace();
                } catch (IOException a) {
                    //  e.printStackTrace();
                } catch (TimeoutException a) {
                    //  e.printStackTrace();
                } catch (Exception a) {

                }

                if (document != null) {
                    domains = parse.ParseHTML(document);
                    //for(int i = 0; i < parse.ParseHTML(document).size();i++)
                    for (int i = 0; i < domains.size(); i++) {
                        System.out.print(parse.getDomains().get(i).getDomainHost() + " - " + parse.getDomains().get(i).getDomainCount() + "\n");
                        lst_domains = new JList(parse.getDomains().toArray());
                    }
                } else
                    System.out.print("HTML document empty, check your url and internet connection");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UrlForms");
        frame.setContentPane(new UrlForms().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
