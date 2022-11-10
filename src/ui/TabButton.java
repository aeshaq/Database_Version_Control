package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.JButton;

public class TabButton extends JButton {
    private String tab;

    public TabButton(String tab) {
        super(tab);
        this.tab = tab;
        
    }

}