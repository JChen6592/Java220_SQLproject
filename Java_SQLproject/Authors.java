
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.table.*;

public class Authors {
                private String firstName, lastName, AuthorID;

                public Authors() {

                }

                public Authors(  String firstName, String lastName,  String AuthorID) {
                    setAID(AuthorID);


                    setFirstName(firstName);
                    setLastName(lastName);

                }

                public void setAID (String AuthorID) {
                    this.AuthorID = AuthorID;

                }

                public void setFirstName (String firstName) {
                    this.firstName = firstName;
                }

                public void setLastName (String lastName){
                    this.lastName = lastName;
                }



                public String getLastName() {
                    return lastName;
                }

                public String getFirstName() {

                    return firstName;
                }


                public String getAuthorID() {
                    return AuthorID;
                }
}
