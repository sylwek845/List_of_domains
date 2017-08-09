import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class UrlForms extends JFrame {
    private JTextField txt_url;
    private JPanel mainPanel;
    private JButton btn_DomainsButton;
    private JList lst_domains;
    private JButton btn_paste;
    private JLabel lbl_state;

    public UrlForms() {
        btn_DomainsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String url = txt_url.getText(); // get value from text box
                if (!url.equals("") || url != null || url != "") { //if textbox is empty
                    Parse parse = new Parse();
                    Document document = null;
                    try {
                        document = parse.getHtml(url); // try to get HTML from URL
                    } catch (IllegalArgumentException a) {
                        lbl_state.setText("Please check your URL and try again.");
                    } catch (IOException a) {
                        lbl_state.setText("Error try again.");
                    } catch (TimeoutException a) {
                        lbl_state.setText("Please check your URL and Internet connection and try again.");
                    } catch (Exception a) {

                    }
                    if (document != null) { // if HTML document isn't null ->
                        DefaultListModel<DomainContainer> domains = parse.ParseHTML(document); // parse HTML and assign return
                        parse = null; // set Parse to null
                        if (domains != null) //check if parsed document isn't null
                            lst_domains.setModel(domains);//add domains to list
                        else
                            lbl_state.setText("Parsing Error, Please try again");

                    } else
                        lbl_state.setText("HTML document empty, Please try again");
                } else
                    lbl_state.setText("Please Enter valid URL");

            }
        });
        btn_paste.addActionListener(new ActionListener() { // add Paste button listner
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable t = c.getContents(this);
                if (t == null)
                    return;
                try {
                    txt_url.setText((String) t.getTransferData(DataFlavor.stringFlavor));
                } catch (Exception s) {
                    s.printStackTrace();
                }//try
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("List of Domains");
        frame.setContentPane(new UrlForms().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
