package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import ui.LoginUI;

public class UIDrawer extends JFrame {

    private JPanel mainPanel;
    private String currentTab;
    private TabButton UserTab;
    private TabButton RepoTab;
    private TabButton OrgTab;
    private JPanel contentPanel;
    private GridBagConstraints c;
     
    private int windowX;
    private int windowY;

    public UIDrawer() {
        super("Main Window");
        this.setMinimumSize(new Dimension(800, 600));
        windowX = this.getWidth();
        windowY = this.getHeight();
        System.out.println(windowX+ ", " + windowY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        this.add(mainPanel);

        JPanel buttons = new JPanel();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(buttons, c);

        JPanel fillerPanel = new JPanel();
        c.gridx = 1;
        c.gridy = 1;
        c.weighty = 1.0;
        c.weightx = 1.0;
        mainPanel.add(fillerPanel, c);

        UserTab = new TabButton("User Login", this);
        RepoTab = new TabButton("Repositories", this);
        OrgTab = new TabButton("Organizations", this);

        buttons.add(UserTab);
        buttons.add(RepoTab);
        buttons.add(OrgTab);

        pack();
        setVisible(true);
        this.setBackground(Color.gray);
    }

    public String getCurrentTab() {
        return currentTab;
    }

    public void updateCurrentTab(String currentTab) {
        this.currentTab = currentTab;
        if (currentTab.equals("User Login")) {
            contentPanel = new LoginUI();
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 1.0;
            c.weightx = 1.0;
            mainPanel.add(contentPanel, c);
            mainPanel.validate();
        }
        System.out.println(currentTab);
        // + more code to change existing buttons
    }

    public static void main(String[] args) {
        new UIDrawer();
    }
}
