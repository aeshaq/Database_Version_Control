package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIDrawer extends JFrame {

    private JPanel mainPanel;
    private String currentTab;
    private TabButton UserTab;
    private TabButton RepoTab;
    private TabButton OrgTab;
     
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
        GridBagConstraints c = new GridBagConstraints();
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

        UserTab = new TabButton("User Login");
        RepoTab = new TabButton("Repositories");
        OrgTab = new TabButton("Organizations");


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
        // + more code to change existing buttons
    }

    public static void main(String[] args) {
        new UIDrawer();
    }
}
