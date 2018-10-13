
import java.sql.*;
import java.util.*;

public class Authorsquery {
    private static final String URL = "jdbc:derby:books";
    private static final String USERNAME = "deitel";
    private static final String PASSWORD = "deitel";

    private Connection connection;
    private PreparedStatement selectAllAuthors;                              //Displays all Authors
    private PreparedStatement selectAuthorsByFirstName;                      //Select a specific Author by first name
    private PreparedStatement insertNewAuthor;                               //Insert a new Author; information about them
    private PreparedStatement selectAuthorsByBookTitle;                      //Select Authors by specific book titles

    public Authorsquery() {
        try
        {
            connection =
                    DriverManager.getConnection(URL, USERNAME, PASSWORD);

            selectAllAuthors =
                    connection.prepareStatement("Select * FROM Authors"  );

            selectAuthorsByFirstName =
                    connection.prepareStatement("SELECT * FROM Authors WHERE FirstName = ?");

            insertNewAuthor =
                    connection.prepareStatement("INSERT INTO Authors" + "(FirstName, LastName)" + "VALUES( ?,? )");

            selectAuthorsByBookTitle =
                    connection.prepareStatement("SELECT * FROM Titles");
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public List< Authors > getallAuthors() {
        List < Authors > results = null;
        ResultSet resultSet = null;

        try {
            resultSet = selectAllAuthors.executeQuery();
            results = new ArrayList< Authors >();

            while (resultSet.next()) {
                results.add(new Authors (
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("AuthorID")));
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return results;
    }

    //select author by book title
    public List <Authors > getAuthorByBookTitle (String bname) {
        List < Authors > results = null;
        ResultSet resultSet = null;

        try {
            selectAuthorsByBookTitle.setString (1, bname); //specify book name

            resultSet = selectAuthorsByBookTitle.executeQuery();

            results = new ArrayList <Authors>();

            while (resultSet.next()) {
                results.add(new Authors(
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("AuthorID")));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return results;
    }

    //Select person by first name
    public List< Authors > getAuthorbyFirstName(String name) {
        List<Authors> results = null;
        ResultSet resultSet = null;

        try {
            selectAuthorsByFirstName.setString(1, name); //specify last name

            //executeQuery and return ResultSet
            resultSet = selectAuthorsByFirstName.executeQuery();

            results = new ArrayList<Authors>();

            while (resultSet.next()) {
                results.add(new Authors(
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("AuthorID")));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                close();
            }
        }
        return results;
    }

    public int addAuthor( String fname, String lname ) {
        int result = 0;

        try{
            insertNewAuthor.setString(1, fname);
            insertNewAuthor.setString(2, lname);

            result = insertNewAuthor.executeUpdate();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }

    public void close() {
        try{
            connection.close();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
