import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

class csvWorker {


  public csvWorker(String nameOfFile) throws FileNotFoundException, SQLException {

    FileReader fileReader = new FileReader(nameOfFile);
    Scanner scanner = new Scanner(fileReader);
    ArrayList<String> list = new ArrayList<>();
    String string = null;
    dbWriter dbWriter = new dbWriter();

    while (scanner.hasNextLine()) {

      string = scanner.nextLine();
      for (String retval : string.split(",")) {
        list.add(retval);
      }

      dbWriter.writerCUSTOMERS(list);
      dbWriter.writeCONTACTS(list);

      dbWriter.id_of_customers++;
      list.clear();
    }

  }
}
