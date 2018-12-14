import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public class formatChooser {
  public formatChooser(String nameOfFile) throws SQLException, ParserConfigurationException, SAXException, IOException {
    int indexcsv = nameOfFile.lastIndexOf(".txt");

    if (indexcsv == -1) {
      xmlWorker xmlWorker = new xmlWorker(nameOfFile);
    } else {
      csvWorker csvWorker = new csvWorker(nameOfFile);
    }
  }


}
