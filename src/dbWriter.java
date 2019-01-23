
import java.sql.*;
import java.util.ArrayList;

class dbWriter {
  private String url = "jdbc:oracle:thin:@localhost:1521:db18c";
  private String username = "system";
  private String password = "Aleks53971";
  public Connection connection;

  public int id_of_customers = 1;
  public int id_of_contact = 1;

  public dbWriter() throws SQLException {

    this.connection = DriverManager.getConnection(this.url, this.username, this.password);
  }

  public void writerCUSTOMERS(ArrayList<String> list) throws SQLException {

    PreparedStatement statement = this.connection.prepareStatement("insert into CUSTOMERS(NAME,SURNAME,AGE) values (?,?,?)");

    statement.setString(1, list.get(0));
    statement.setString(2, list.get(1));
    statement.setString(3, list.get(2));
    statement.executeUpdate();

  }

  public void writeCONTACTS(ArrayList<String> list) throws SQLException {
    PreparedStatement statement = this.connection.prepareStatement("insert into CONTACTS(ID_CUSTOMER,TYPE,CONTACT) values (?,?,?)");
    Statement statement1 = connection.createStatement();




    ResultSet rs = statement1.executeQuery("SELECT max(ID_CUSTOMER) from CUSTOMERS");
    int LastCustomer = 0;
    if(rs.next())
     LastCustomer = rs.getInt(1);
    for (int i = 3; i < list.size(); i++) {

      statement.setInt(1, LastCustomer);
      String regex = "[\\d\\s]+";
      if (list.get(i).matches(regex) && (list.get(i).length() == 9) || list.get(i).length() == 11) //phone
      {
        statement.setInt(2, 2);
      } else {

        if (list.get(i).equals("jbr"))//jabber
        {

          statement.setInt(2, 3);
        } else {
          boolean itsmail = false;
          for (int j = 0; j < list.get(i).length(); j++) {
            if (list.get(i).charAt(j) == '@') {
              statement.setInt(2, 1);//mail
              itsmail = true;
            }
          }
          if (itsmail == false)
            statement.setInt(2, 0);//unknown
        }
      }
      statement.setString(3, list.get(i));
      statement.executeUpdate();
    }

  }
}
