import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by : Sylwester Zalewski
 * Date: 09/09/2017
 * <p>
 * Forms class, JForms, Threads
 */

public class UrlForms extends JFrame {
    private JTextField txt_url;
    private JPanel mainPanel;
    private JButton btn_DomainsButton;
    private JList lst_domains;
    private JButton btn_paste;
    private JLabel lbl_state;
    private String[] protocols = {"", "http://", "https://", "https://www"};

    public UrlForms() {
        btn_DomainsButton.addActionListener(e -> {
            //Lock Buttons while working
            btn_DomainsButton.setEnabled(false);
            btn_paste.setEnabled(false);

            Thread parseURLThread = new Thread(() -> { //new thread, lambda
                doTask();

                synchronized (UrlForms.this) {
                    UrlForms.this.unlock_buttons();  // Unlock buttons when finished
                }
            }, "ParseURLThread");

            parseURLThread.start();
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void unlock_buttons() //method to unlock buttons
    {
        btn_DomainsButton.setEnabled(true);
        btn_paste.setEnabled(true);
    }

    private void doTask() {
        if (!txt_url.getText().isEmpty()) { //if textbox is empty
            Parse parse = new Parse();
            boolean url_error = false;
            Document document = null;
            lbl_state.setText("Working, Please Wait!");
            for (int i = 0; i < protocols.length; i++) { //Try to Eliminate human error of missing https etc
                try {
                    document = parse.getHtml(protocols[i] + txt_url.getText()); // try to get HTML from URL//
                } catch (Exception a) {
                    //Invalid URL or Connection Error
                    if (i == protocols.length) { // if fixing failed
                        url_error = true;
                        lbl_state.setText("Error, Please Check your URL");
                    }
                }
            }
            if (document != null) { // if HTML document isn't null ->
                DefaultListModel<DomainContainer> domains = parse.ParseHTML(document); // parse HTML and assign return
                // parse = null; // set Parse to null
                if (domains != null) {//check if parsed document isn't null
                    lst_domains.setModel(domains);//add domains to list
                    lbl_state.setText("Done!");
                } else
                    lbl_state.setText("Parsing Error, Please try again");

            } else if (!url_error)
                lbl_state.setText("Error, HTML document empty, check website.");

        } else
            lbl_state.setText("Please Enter valid URL");
    }
}
