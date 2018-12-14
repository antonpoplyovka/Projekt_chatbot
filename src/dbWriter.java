
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

class dbWriter {
  private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
  private String username = "system";
  private String password = "system123";
  public Connection connection;

  public int id_of_customers = 1;
  public int id_of_contact = 1;

  public dbWriter() throws SQLException {

    this.connection = DriverManager.getConnection(this.url, this.username, this.password);
  }

  public void writerCUSTOMERS(ArrayList<String> list) throws SQLException {

    PreparedStatement statement = this.connection.prepareStatement("insert into CUSTOMERS(ID,NAME,SURNAME,AGE) values (?,?,?,?)");
    statement.setInt(1, this.id_of_customers);
    statement.setString(2, list.get(0));
    statement.setString(3, list.get(1));
    statement.setString(4, list.get(2));
    statement.executeUpdate();

  }

  public void writeCONTACTS(ArrayList<String> list) throws SQLException {
    PreparedStatement statement = this.connection.prepareStatement("insert into CONTACTS(ID,ID_CUSTOMER,TYPE,CONTACT) values (?,?,?,?)");

    for (int i = 3; i < list.size(); i++) {
      statement.setInt(1, this.id_of_contact);
      this.id_of_contact++;
      statement.setInt(2, this.id_of_customers);
      String regex = "[\\d\\s]+";
      if (list.get(i).matches(regex) && (list.get(i).length() == 9) || list.get(i).length() == 11) //phone
      {
        statement.setInt(3, 2);
      } else {

        if (list.get(i).equals("jbr"))//jabber
        {

          statement.setInt(3, 3);
        } else {
          boolean itsmail = false;
          for (int j = 0; j < list.get(i).length(); j++) {
            if (list.get(i).charAt(j) == '@') {
              statement.setInt(3, 1);//mail
              itsmail = true;
            }
          }
          if (itsmail == false)
            statement.setInt(3, 0);//unknown
        }
      }
      statement.setString(4, list.get(i));
      statement.executeUpdate();
    }

  }
}
