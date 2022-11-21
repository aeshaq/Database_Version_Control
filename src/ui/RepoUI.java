package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RepoUI extends JPanel {
    private JTextField repoSearch;

    private JPanel repoPanel;
    private Button repoEnterButton;
    private Button selectRepo;
    private JList repoList;
    private JScrollPane repoScrollPane;
    private JLabel repoLabel;

    private JPanel filePanel;
    private Button fileEnterButton;
    private Button displayFile;
    private JList FileList;
    private JScrollPane fileScrollPane;
    private JLabel fileLabel;
    private JTextArea extensionList;
    private JScrollPane extensionScroller;
    

    private JPanel contentPanel;
    private JTextField filePath;
    private JTextField fileName;
    private JTextArea contents;
    private JScrollPane contentsScroll;
    private Button createFileButton;
    private JLabel contentLabel;

    private JPanel fileEditPanel;
    private Button deleteFile;
    private Button commit;
    private JLabel fileEditLabel;
    private JLabel commitLabel;
    private JTextArea changes;
    private JScrollPane changesScroll;
    private JTextField linesChanged;

    public RepoUI() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.weightx = 5.0;
        c.weighty = 5.0;


        repoPanel = buildRepoPanel();
        repoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        c.gridx = 0;
        c.gridy = 0;
        this.add(repoPanel, c);

        filePanel = buildFilePanel();
        filePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        c.gridx = 1;
        c.gridy = 0;
        this.add(filePanel, c);


        JPanel column3Panel = new JPanel(); 
        column3Panel.setLayout(new GridBagLayout());

        contentPanel = buildContentPanel();
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        c.gridx = 0;
        c.gridy = 0;
        column3Panel.add(contentPanel, c);

        fileEditPanel = buildFileEditPanel();
        fileEditPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        c.gridx = 0;
        c.gridy = 1;
        column3Panel.add(fileEditPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        this.add(column3Panel, c);
        
    }


    private JPanel buildRepoPanel() {
        JPanel myPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        
        c.weightx = 5.0;
        c.gridx = 0;
 
        myPanel.setLayout(new GridBagLayout());

        //No fill

        repoEnterButton = new Button("Enter");
        c.gridy = 2;
        myPanel.add(repoEnterButton, c);

        selectRepo = new Button("Select Repository");
        c.gridy = 4;
        myPanel.add(selectRepo, c);
         
        repoLabel = new JLabel("Search repository here:");
        c.gridy = 0;
        myPanel.add(repoLabel, c);

        //Horizontal fill
        c.fill = GridBagConstraints.HORIZONTAL;
        repoSearch = new JTextField("Search Repositories");
        c.gridy = 1;
        myPanel.add(repoSearch, c);

        //Both fill
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 5.0;
        String l[] = {"suncor", " save on foods", " github", "here"};
        repoList = new JList(l);
        repoScrollPane= new JScrollPane(repoList);
        repoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridy = 3;
        myPanel.add(repoScrollPane, c);

        return myPanel;
    }

    private JPanel buildFilePanel() {
        JPanel myPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        
        c.weightx = 5.0;
        c.gridx = 0;
 
        myPanel.setLayout(new GridBagLayout());

        //No fill

        c.weightx = 5.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;

        //No fill
        fileEnterButton = new Button("Enter");
        c.gridy = 2;
        myPanel.add(fileEnterButton, c);

        fileLabel = new JLabel("Search file here:");
        c.gridy = 0;
        myPanel.add(fileLabel, c);

        displayFile = new Button("Display File");
        c.gridy = 4;
        myPanel.add(displayFile, c);

        //Horizontal fill
        c.fill = GridBagConstraints.HORIZONTAL;
        repoSearch = new JTextField("Search Files");
        c.gridy = 1;
        myPanel.add(repoSearch, c);

        //Both fill
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 5.0;
        String m[] = {"file1", "readme", " dontreadme", "private"};
        FileList = new JList(m);
        fileScrollPane= new JScrollPane(FileList);
        fileScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridy = 3;
        myPanel.add(fileScrollPane, c);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        extensionList = new JTextArea("Extensions:\n.js, .lol, .xlsx, .mp4");
        extensionScroller= new JScrollPane(extensionList);
        extensionScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridy = 5;
        myPanel.add(extensionScroller, c);

        return myPanel;
    }

    private JPanel buildContentPanel() {
        JPanel myPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        myPanel.setLayout(new GridBagLayout());
        
        c.weightx = 5.0;
        c.gridx = 0;

        c.weightx = 5.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;

        createFileButton = new Button("Create File");
        c.gridy = 4;
        myPanel.add(createFileButton, c);

        contentLabel = new JLabel("Create new file:");
        c.gridy = 0;
        myPanel.add(contentLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        filePath = new JTextField("File Path");
        c.gridy = 1;
        myPanel.add(filePath, c);
        fileName = new JTextField("File Name");
        c.gridy = 2;
        myPanel.add(fileName, c);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 5.0;
        contents = new JTextArea("File Contents");
        contentsScroll= new JScrollPane(contents);
        contentsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridy = 3;
        myPanel.add(contentsScroll, c);

        return myPanel;
    }

    private JPanel buildFileEditPanel() {
        JPanel myPanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        myPanel.setLayout(new GridBagLayout());
        
        c.weightx = 5.0;
        c.gridx = 0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;

        deleteFile = new Button("Delete File");
        c.gridy = 1;
        myPanel.add(deleteFile, c);

        commit = new Button("Commit Changes");
        c.gridy = 5;
        myPanel.add(commit, c);

        commitLabel = new JLabel("Commit to currently selected file");
        c.gridy = 2;
        myPanel.add(commitLabel, c);

        fileEditLabel = new JLabel("Operations with currently selected file: ");
        c.gridy = 0;
        myPanel.add(fileEditLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        linesChanged = new JTextField("Lines changed:");
        c.gridy = 4;
        myPanel.add(linesChanged, c);


        c.fill = GridBagConstraints.BOTH;
        c.weighty = 5.0;
        changes = new JTextArea("Enter new file contents here");
        changesScroll= new JScrollPane(changes);
        changesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridy = 3;
        myPanel.add(changesScroll, c);
        
        return myPanel;
    }
}