import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.xml.sax.SAXException;
import org.telegram.telegrambots.TelegramBotsApi;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class Main  {
  public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, SAXException, ParserConfigurationException {
    System.out.println("Input name of file: ");
    Scanner scanner = new Scanner(System.in);
    formatChooser formatChooser = new formatChooser(scanner.nextLine());


  }
}
