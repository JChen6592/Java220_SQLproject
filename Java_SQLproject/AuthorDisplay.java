
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;

public class AuthorDisplay extends JFrame {
    private Authors currentEntry;
    private Authorsquery aQueries;
    private List<Authors> results;
    private int numberofEntries = 0;
    private int currentEntryIndex;

    private JButton browseButton;
    private JLabel firstNameLabel;
    private JTextField firstNameTextField;
    private JLabel idLabel;
    private JTextField idTextField;
    private JTextField indexTextField;
    private JLabel lastNameLabel;
    private JTextField lastNameTextField;
    private JTextField maxTextField;
    private JButton nextButton;
    private JLabel ofLabel;
    private JButton previousButton;
    private JButton queryButton;
    private JLabel queryLabel;
    private JPanel queryPanel;
    private JPanel navigatePanel;
    private JPanel displayPanel;
    private JTextField queryTextField;
    private JButton insertButton;

    //constructor
    public AuthorDisplay() {
        super("Authors Book");

        //establish database connection and set PreparedStatement
        aQueries = new Authorsquery();

        //create GUI
        navigatePanel = new JPanel();
        previousButton = new JButton();
        indexTextField = new JTextField(2);
        ofLabel = new JLabel();
        maxTextField = new JTextField(2);
        nextButton = new JButton();
        displayPanel = new JPanel();
        idLabel = new JLabel();
        idTextField = new JTextField(10);
        firstNameLabel = new JLabel();
        firstNameTextField = new JTextField(10);
        lastNameLabel = new JLabel();
        lastNameTextField = new JTextField(10);
        queryPanel = new JPanel();
        queryLabel = new JLabel();
        queryTextField = new JTextField(10);
        queryButton = new JButton();
        browseButton = new JButton();
        insertButton = new JButton();

        setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        setSize(400,300);
        setResizable(true);

        navigatePanel.setLayout(new BoxLayout(navigatePanel, BoxLayout.X_AXIS));

        previousButton.setText("Previous");
        previousButton.setEnabled(false);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                previousButtonActionPerformed(evt);
            }
        });

        navigatePanel.add(previousButton);
        navigatePanel.add(Box.createHorizontalStrut(10));

        indexTextField.setHorizontalAlignment(JTextField.CENTER);
        indexTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                indexTextFieldActionPerformed(evt);
            }
        });

        navigatePanel.add(indexTextField);
        navigatePanel.add(Box.createHorizontalStrut(10));

        ofLabel.setText("of");
        navigatePanel.add(ofLabel);
        navigatePanel.add(Box.createHorizontalStrut(10));

        maxTextField.setHorizontalAlignment(JTextField.CENTER);
        maxTextField.setEditable(false);
        navigatePanel.add(maxTextField);
        navigatePanel.add(Box.createHorizontalStrut(10));

        nextButton.setText("Next");
        nextButton.setEnabled(false);
        nextButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt){
               nextButtonActionPerformed(evt);
           }
        });

        navigatePanel.add(nextButton);
        add(navigatePanel);

        displayPanel.setLayout(new GridLayout(5,2,4,4));

        idLabel.setText("Author ID:");
        displayPanel.add(idLabel);

        idTextField.setEditable(false);
        displayPanel.add(idTextField);

        firstNameLabel.setText("First Name:");
        displayPanel.add(firstNameLabel);
        displayPanel.add(firstNameTextField);

        lastNameLabel.setText("Last Name:");
        displayPanel.add(lastNameLabel);
        displayPanel.add(lastNameTextField);
        add(displayPanel);

        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.X_AXIS));

        queryPanel.setBorder(BorderFactory.createTitledBorder("Find an Author by their first name or Book Title"));
        queryLabel.setText("First name or Book Title:");
        queryPanel.add(Box.createHorizontalStrut(5));
        queryPanel.add(queryLabel);
        queryPanel.add(Box.createHorizontalStrut(10));
        queryPanel.add(queryTextField);
        queryPanel.add(Box.createHorizontalStrut(10));

        queryButton.setText("Find");
        queryButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                queryButtonActionPerformed(evt);
            }
        });

        queryPanel.add(queryButton);
        queryPanel.add(Box.createHorizontalStrut(5));
        add(queryPanel);

        queryPanel.add(queryButton);
        queryPanel.add(Box.createHorizontalStrut(5));
        add(queryPanel);

        browseButton.setText("Browse All Entries");
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        add(browseButton);

        insertButton.setText("Insert New Entry");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                insertButtonActionPerformed(evt);
            }
        });

        add(insertButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                aQueries.close(); // close database connection
                System.exit(0);
            }
        });

        setVisible(true);
    } //end of constructor

    //when previous button is clicked -- current index
    private void previousButtonActionPerformed(ActionEvent evt){
        currentEntryIndex--;  //decrement current

        if (currentEntryIndex < 0){
            currentEntryIndex = numberofEntries - 1;
        }

        indexTextField.setText("" + (currentEntryIndex + 1));
        indexTextFieldActionPerformed(evt);
    }

    //when the next button is clicked ++ current index
    private void nextButtonActionPerformed(ActionEvent evt){
        currentEntryIndex++;  //increment current

        if(currentEntryIndex >= numberofEntries){
            currentEntryIndex = 0;
        }

        indexTextField.setText("" + (currentEntryIndex + 1));
        indexTextFieldActionPerformed(evt);
    }

    //when the query button is clicked
    private void queryButtonActionPerformed(ActionEvent evt) {
        results = aQueries.getAuthorbyFirstName(queryTextField.getText());
        numberofEntries = results.size();

        if (numberofEntries != 0) {
            currentEntryIndex = 0;
            currentEntry = results.get(currentEntryIndex);
            idTextField.setText("" + currentEntry.getAuthorID());
            firstNameTextField.setText(currentEntry.getFirstName());
            lastNameTextField.setText(currentEntry.getLastName());
            maxTextField.setText("" + numberofEntries);
            indexTextField.setText("" + (currentEntryIndex + 1));
            nextButton.setEnabled(true);
            previousButton.setEnabled(true);
        }
        else {
            browseButtonActionPerformed(evt);
        }
    }

    //When new data is updated
    private void indexTextFieldActionPerformed(ActionEvent evt) {
        currentEntryIndex = (Integer.parseInt(indexTextField.getText()) - 1);

        if (numberofEntries != 0 && currentEntryIndex < numberofEntries){
            currentEntry = results.get(currentEntryIndex);
            idTextField.setText("" + currentEntry.getAuthorID());
            firstNameTextField.setText(currentEntry.getFirstName());
            lastNameTextField.setText(currentEntry.getLastName());
            maxTextField.setText("" + numberofEntries);
            indexTextField.setText("" + (currentEntryIndex + 1));
        }
    }

    //when user decides to browse
    private void browseButtonActionPerformed(ActionEvent evt){
        try {
            results = aQueries.getallAuthors();
            numberofEntries = results.size();

            if(numberofEntries != 0 && currentEntryIndex < numberofEntries){
                currentEntry = results.get(currentEntryIndex);
                idTextField.setText("" + currentEntry.getAuthorID());
                firstNameTextField.setText(currentEntry.getFirstName());
                lastNameTextField.setText(currentEntry.getLastName());
                maxTextField.setText("" + numberofEntries);
                indexTextField.setText("" + (currentEntryIndex + 1));
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //insertbutton is clicked
    private void insertButtonActionPerformed(ActionEvent evt){
        int result = aQueries.addAuthor(firstNameTextField.getText(), lastNameTextField.getText() );

        if (result == 1) {
            JOptionPane.showMessageDialog(this, "New Author has been added to database", "Added Successfully!", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "You've encountered an issue", "Error 404:", JOptionPane.PLAIN_MESSAGE);
        }

        browseButtonActionPerformed(evt);
    }

    //run the code
    public static void main(String args[]){

        new AuthorDisplay();
    }
}

