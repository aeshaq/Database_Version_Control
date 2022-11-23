package ui;

import javax.swing.*;
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
    private JTextArea megaTitle;
    private JLabel aveCommits;

    public OrgsUI() {
        GridBagConstraints c = new GridBagConstraints();
        
        c.weightx = 5.0;
 
        this.setLayout(new GridBagLayout());

        //No fill
        aveCommits = new JLabel("Average number of commits per user: 10");
        c.gridx = 3;
        c.gridy = 1;
        c.weightx = 2.0;
        this.add(aveCommits, c);
        c.weightx = 5.0;


        enterButton = new Button("Enter");
        c.gridx = 0;
        c.gridy = 1;

        this.add(enterButton, c);

        addToUser = new Button("Add org to current user");
        c.gridx = 0;
        c.gridy = 4;
        this.add(addToUser, c);

        megaTitle = new JTextArea("Mega Users List");
        c.gridx = 1;
        c.gridy = 1;
        megaTitle.setEditable(false);
        this.add(megaTitle, c);

        //Horizontal fill
        c.fill = GridBagConstraints.HORIZONTAL;
        username = new JTextField("Search Organizations");
        c.gridx = 0;
        c.gridy = 0;
        this.add(username, c);

        //Both fill
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 5.0;
        String l[] = {"suncor", " save on foods", " github", "here"};
        orgList = new JList(l);

        orgScrollPane= new JScrollPane(orgList);
        orgScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridx = 0;
        c.gridy = 3;
   
        this.add(orgScrollPane, c);

        String m[] = {"john",  "james ", " Jeremy",  "Jones", "jajajja"};
        megaList = new JList(m);
        megaScrollPane= new JScrollPane(megaList);
        megaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridx = 1;
        c.gridy = 3;
        this.add(megaScrollPane, c);

        addListener();
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