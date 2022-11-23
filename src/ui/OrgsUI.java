package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrgsUI extends JPanel {
    private JTextField username;
    private Button enterButton;
    private Button addToUser;
    private JList orgList;
    private JScrollPane orgScrollPane;
    private JList megaList;
    private JScrollPane megaScrollPane;
    private JLabel megaTitle;
    private JLabel lbel3;
    private JLabel mostUsed;
    private JLabel aveCommits;

    private JPanel beforePanel;
    private JPanel afterPanel;


    public OrgsUI() {
        GridBagConstraints c = new GridBagConstraints();
        
        c.weightx = 5.0;
        c.weighty = 5.0;
        c.fill = GridBagConstraints.BOTH;
 
        this.setLayout(new GridBagLayout());

        beforePanel = buildBeforeSelectPanel();
        afterPanel = buildAfterSelectPanel();
        beforePanel.setBorder(new EmptyBorder(10, 10, 10, 20));
        c.gridx = 0;
        c.gridy = 0;
        this.add(beforePanel, c);
        c.gridx = 1;
        c.gridy = 0;
        this.add(afterPanel, c);


       



        addListener();
    }

    private JPanel buildBeforeSelectPanel() {
        JPanel myPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        myPanel.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.NONE;
        c.weightx = 5.0;

        enterButton = new Button("Enter");
        c.gridx = 0;
        c.gridy = 1;

        myPanel.add(enterButton, c);

        addToUser = new Button("Add org to current user");
        c.gridx = 0;
        c.gridy = 3;
        myPanel.add(addToUser, c);


        //Horizontal fill
        c.fill = GridBagConstraints.HORIZONTAL;
        username = new JTextField("Search Organizations");
        c.gridx = 0;
        c.gridy = 0;
        myPanel.add(username, c);

        //Both fill
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 5.0;
        String l[] = {"suncor", " save on foods", " github", "here"};
        orgList = new JList(l);

        orgScrollPane= new JScrollPane(orgList);
        orgScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridx = 0;
        c.gridy = 2;
   
        myPanel.add(orgScrollPane, c);
        return myPanel;

    }

    private JPanel buildAfterSelectPanel() {
        JPanel myPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        myPanel.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.NONE;
        c.weightx = 2.0;
        
        c.gridx = 0;

        aveCommits = new JLabel("Average number of commits per user: 10");
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 2.0;
        myPanel.add(aveCommits, c);

        lbel3 = new JLabel("something else?");
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 2.0;
        myPanel.add(lbel3, c);

        mostUsed = new JLabel("Most commited repo: here");
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 2.0;
        myPanel.add(mostUsed, c);
        c.weightx = 5.0;

        c.fill = GridBagConstraints.BOTH;
        
        megaTitle = new JLabel("Mega Users List");
        c.gridx = 0;
        c.gridy = 0;
        myPanel.add(megaTitle, c);

        c.weighty = 5.0;
        String m[] = {"john",  "james ", " Jeremy",  "Jones", "jajajja"};
        megaList = new JList(m);
        megaScrollPane= new JScrollPane(megaList);
        megaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridx = 0;
        c.gridy = 3;
        myPanel.add(megaScrollPane, c);

        return myPanel;
    }



    protected void addListener() {
        enterButton.addActionListener(new EnterPressHandler(username));
        addToUser.addActionListener(new AddOrgPressHandler());
    }

    private class EnterPressHandler implements ActionListener {
        JTextField content;
        private EnterPressHandler(JTextField content) {
            this.content = content;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(content.getText());
        }
    }

    private class AddOrgPressHandler implements ActionListener {
        private AddOrgPressHandler() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Added (not really)");
        }
    }
}