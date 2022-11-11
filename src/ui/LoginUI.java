package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginUI extends JPanel {
    private JTextField username;
    private JTextField password;
    private Button enterButton;

    public LoginUI() {
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        username = new JTextField("username");
        c.gridx = 0;
        c.gridy = 0;
        this.add(username, c);
        password = new JTextField("password");
        c.gridx = 0;
        c.gridy = 1;
        this.add(password, c);
        enterButton = new Button("Enter");
        c.gridx = 0;
        c.gridy = 2;
        this.add(enterButton, c);
    }


}