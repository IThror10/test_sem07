package TermPedia;

import TermPedia.dialogs.*;
import TermPedia.dto.Term;
import TermPedia.events.user.User;
import TermPedia.panels.*;

import javax.swing.*;
import javax.swing.border.Border;

public class ApplicationFrame extends javax.swing.JFrame {
    private JButton activeButton;
    private JButton searchByAuthor;
    private JButton searchByTerm;
    private JButton searchByBook;
    private JButton searchByTag;

    private Border pressedBorder;
    private JPanel activePanel;

    private User user;
    private int width;
    private int height;
    public ApplicationFrame(int width, int height) {
        super("TermPedia Desktop");
        this.width = width;
        this.height = height;
        this.user = null;

        this.initializeFrame();
        this.initializeMenuBar();
        this.initializeWindow();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initializeFrame() {
        this.setLayout(null);
        this.setSize(width, height);
        this.setResizable(false);
    }

    private void initializeWindow() {
        int panelWidth = width;
        int panelHeight = height / 14 * 11;

        TagSearchPanel tagSearchPanel;
        BookSearchPanel bookSearchPanel;
        TermSearchPanel termSearchPanel;
        AuthorSearchPanel authorSearchPanel;

        //Panels
        bookSearchPanel = new BookSearchPanel(panelWidth, panelHeight);
        bookSearchPanel.setBounds(0, height / 14, panelWidth, panelHeight);
        this.add(bookSearchPanel);

        tagSearchPanel = new TagSearchPanel(panelWidth, panelHeight);
        tagSearchPanel.setBounds(0, height / 14, panelWidth, panelHeight);
        this.add(tagSearchPanel);

        termSearchPanel = new TermSearchPanel(panelWidth, panelHeight);
        termSearchPanel.setBounds(0, height / 14, panelWidth, panelHeight);
        this.add(termSearchPanel);

        authorSearchPanel = new AuthorSearchPanel(panelWidth, panelHeight);
        authorSearchPanel.setBounds(0, height / 14, panelWidth, panelHeight);
        this.add(authorSearchPanel);


        //Buttons
        searchByAuthor = new JButton("Search by author");
        searchByAuthor.setBounds(0, 0, width / 4, height / 14);
        this.add(searchByAuthor);

        searchByAuthor.addActionListener(e -> {
            changeActiveButton(searchByAuthor);
            changeActivePanel(authorSearchPanel);
        });


        searchByTerm = new JButton("Search by term");
        searchByTerm.setBounds(width / 4, 0, width / 4, height / 14);
        this.add(searchByTerm);

        searchByTerm.addActionListener(e -> {
            changeActiveButton(searchByTerm);
            changeActivePanel(termSearchPanel);
        });


        searchByBook = new JButton("Search by name");
        searchByBook.setBounds(width / 2, 0, width / 4, height / 14);
        this.add(searchByBook);

        searchByBook.addActionListener(e -> {
            changeActiveButton(searchByBook);
            changeActivePanel(bookSearchPanel);
        });


        searchByTag = new JButton("Search by tags");
        searchByTag.setBounds(width / 4 * 3, 0, width / 4, height / 14);
        this.add(searchByTag);

        searchByTag.addActionListener(e -> {
            changeActiveButton(searchByTag);
            changeActivePanel(tagSearchPanel);
        });


        pressedBorder = BorderFactory.createLoweredBevelBorder();
        activePanel = tagSearchPanel;
        activePanel.setVisible(true);
        activeButton = searchByTag;
        activeButton.setBorder(pressedBorder);
    }

    private void initializeMenuBar() {
        TagsPanel tagsPanel;
        LitsPanel litsPanel;
        JMenuBar menuBar;

        JMenu menuAdd, menuUser;
        JMenuItem addTerm, addTag, addLit, register, authorize, logout;

        int panelWidth = width;
        int panelHeight = height / 14 * 11;


        menuBar = new JMenuBar();
        menuAdd = new JMenu("Add");
        menuUser = new JMenu("Account");

        litsPanel = new LitsPanel(panelWidth, panelHeight);
        litsPanel.setBounds(0, height / 14, panelWidth, panelHeight);
        this.add(litsPanel);

        tagsPanel = new TagsPanel(panelWidth, panelHeight);
        tagsPanel.setBounds(0, height / 14, panelWidth, panelHeight);
        this.add(tagsPanel);

        addTerm = new JMenuItem("Term");
        menuAdd.add(addTerm);
        addTerm.addActionListener(e -> {
            AddTermDialog dialog = new AddTermDialog();
            dialog.execute(user);
        });

        addTag = new JMenuItem("Tag");
        menuAdd.add(addTag);
        addTag.addActionListener(e -> {
            FindTermDialog dialog = new FindTermDialog();
            Term term = dialog.execute();
            if (term != null) {
                tagsPanel.refresh(term);
                changeActivePanel(tagsPanel);
            }
        });

        addLit = new JMenuItem("Literature");
        menuAdd.add(addLit);
        addLit.addActionListener(e -> {
            FindTermDialog dialog = new FindTermDialog();
            Term term = dialog.execute();
            if (term != null) {
                litsPanel.refresh(term);
                changeActivePanel(litsPanel);
            }
        });

        register = new JMenuItem("Register");
        menuUser.add(register);
        register.addActionListener(e -> {
            RegisterDialog dialog = new RegisterDialog();
            dialog.execute();
        });

        authorize = new JMenuItem("Log in");
        menuUser.add(authorize);
        authorize.addActionListener(e -> {
            AuthorizeDialog dialog = new AuthorizeDialog();
            User user = dialog.execute();
            if (user != null) {
                this.user = user;
                tagsPanel.setUser(user);
                litsPanel.setUser(user);
            }
        });

        logout = new JMenuItem("Log out");
        menuUser.add(logout);
        logout.addActionListener(e -> {
            LogoutDialog dialog = new LogoutDialog();
            boolean res = dialog.execute(this.user == null ? null : this.user.login);
            if (res) {
                this.user = null;
                tagsPanel.setUser(null);
                litsPanel.setUser(null);
            }
        });

        menuBar.add(menuAdd);
        menuBar.add(menuUser);
        this.setJMenuBar(menuBar);
    }

    private void changeActiveButton(JButton curActive) {
        activeButton.setBorder(null);
        activeButton = curActive;
        activeButton.setBorder(pressedBorder);
    }

    private void changeActivePanel(JPanel curActive) {
        activePanel.setVisible(false);
        activePanel = curActive;
        curActive.setVisible(true);
    }
}
