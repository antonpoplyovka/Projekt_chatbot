
import org.json.JSONObject;
import org.telegram.telegrambots.ApiContextInitializer;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.xml.sax.SAXException;
import org.telegram.telegrambots.TelegramBotsApi;


import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Main extends TelegramLongPollingBot {

  public static void main(String[] args) {


    ApiContextInitializer.init();

    TelegramBotsApi botapi = new TelegramBotsApi();
    try {
      botapi.registerBot(new Main());
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }

  }

  @Override
  public String getBotUsername() {
    return "hadcmd";
    
  }

  @Override
  public void onUpdateReceived(Update update) {


    Message message = update.getMessage();
    if (message != null && message.hasDocument()) {
      Document document = message.getDocument();
      try {
        uploadFile(document.getFileName(), document.getFileId());
      } catch (IOException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (SAXException e) {
        e.printStackTrace();
      } catch (ParserConfigurationException e) {
        e.printStackTrace();
      }


    } else if (message != null && message.hasText()) {
      try {
        doCommand(message, message.getText());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }


  }

  public void sendMsg(Message message, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(message.getChatId().toString());
    sendMessage.setReplyToMessageId(message.getMessageId());
    sendMessage.setText(text);
    try {
      sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  public void doCommand(Message message, String command) throws SQLException {
    Connection connection;
    connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:db18c", "system", "Aleks53971");
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(command);
    ResultSetMetaData resultSetMetaData = rs.getMetaData();
    System.out.println(rs.toString());

    while (rs.next()) {
      String out = null;
      for (int i = 1; i < resultSetMetaData.getColumnCount(); i++)
        out += rs.getString(i) + " ";
      System.out.println(out);
      sendMsg(message, out);
    }
  }


  public void uploadFile(String file_name, String file_id) throws IOException, SQLException, ParserConfigurationException, SAXException {
    URL url = new URL("https://api.telegram.org/bot" + getBotToken() + "/getFile?file_id=" + file_id);
    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    String res = in.readLine();
    JSONObject jresult = new JSONObject(res);
    JSONObject path = jresult.getJSONObject("result");
    String file_path = path.getString("file_path");
    URL downoload = new URL("https://api.telegram.org/file/bot" + getBotToken() + "/" + file_path);
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    System.out.println(timeStamp);
    FileOutputStream fos = new FileOutputStream("Project_rekr" + timeStamp + file_name);//+ //Integer.toString(returnIdent()));
    System.out.println("Start upload");
    ReadableByteChannel rbc = Channels.newChannel(downoload.openStream());
    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    fos.close();
    rbc.close();
    formatChooser formatChooser = new formatChooser("Project_rekr" + timeStamp + file_name);

    System.out.println("Uploaded!");
  }

  @Override
  public String getBotToken() {
    return "581778566:AAEQGfEriLg9m0fIDGwCIY3yH1kCPBFvUhg";

  }
}
