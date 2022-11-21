package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginUI extends JPanel {
    private JTextField username;
    private JTextField password;
    private Button enterButton;
    private JLabel activeUser;

    public LoginUI() {
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        username = new JTextField("username");
        username.setPreferredSize(new Dimension(150, (int)username.getPreferredSize().getHeight()));
        c.gridx = 0;
        c.gridy = 0;
        this.add(username, c);
        password = new JTextField("password");
        password.setPreferredSize(new Dimension(150, (int)password.getPreferredSize().getHeight()));
        c.gridx = 0;
        c.gridy = 1;
        this.add(password, c);
        enterButton = new Button("Enter");
        c.gridx = 0;
        c.gridy = 2;
        this.add(enterButton, c);
        activeUser = new JLabel("Currently logged in as: None");
        c.gridx = 0;
        c.gridy = 3;
        this.add(activeUser, c);

    }


}