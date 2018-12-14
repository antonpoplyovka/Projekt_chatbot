import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


class xmlWorker {


  public xmlWorker(String fileName) throws ParserConfigurationException, IOException, SAXException, SQLException {
    DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
    Document document = (Document) documentBuilder.parse(fileName);
    ArrayList<String> listaString = new ArrayList<>();
    dbWriter dbWriter = new dbWriter();
    NodeList nodeList = document.getElementsByTagName("person");
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        NodeList peopleNList = element.getChildNodes();


        listaString.add(String.valueOf(peopleNList.item(1).getTextContent()));
        listaString.add(String.valueOf(peopleNList.item(3).getTextContent()));
        if (String.valueOf(peopleNList.item(5).getNodeName()) == "city") {
          listaString.add(null);
          listaString.add(String.valueOf(peopleNList.item(5).getTextContent()));
        } else {
          listaString.add(String.valueOf(peopleNList.item(5).getTextContent()));
          listaString.add(String.valueOf(peopleNList.item(7).getTextContent()));
        }
        String string = String.valueOf(element.getElementsByTagName("contacts").item(0).getTextContent());

        for (String retval : string.split("\n")) {
          retval = retval.trim();
          if (retval.equals(""))
            continue;
          listaString.add(retval);
        }


      }
      dbWriter.writerCUSTOMERS(listaString);
      dbWriter.writeCONTACTS(listaString);

      dbWriter.id_of_customers++;
      listaString.clear();

    }

  }
}
