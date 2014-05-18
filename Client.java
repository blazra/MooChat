import java.net.*;
import java.io.*;
import java.util.*;
import javafx.collections.*;
 
public class Client extends Thread
{
    private int portNumber;
    private String serverUrl;

    private Map<String, ContactHandler> contactHandlerMap;
    private ObservableList<String> observableContactList;

    Client(String serverUrl, int portNumber)
    {
        this.portNumber = portNumber;
        this.serverUrl = serverUrl;

        contactHandlerMap = new HashMap<String, ContactHandler>();
        observableContactList = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    }

    public void run()
    {
        while(true)
        {
            try
            {
                Socket clientSocket;

                clientSocket = new Socket(serverUrl, portNumber);
                
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine, outputLine;

                out.println(Ui.getNick());
                String nick = in.readLine();

                ContactHandler contactHandler = new ContactHandler(out, in, nick);
                contactHandler.setDaemon(true);
                contactHandler.start();

                contactHandlerMap.put(nick, contactHandler);
                observableContactList.add(nick);
                break;                
            }
            catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection - retrying in 5 seconds");
                System.out.println(e.getMessage());
                try
                {
                    Thread.sleep(5000);
                }
                catch(InterruptedException ie){}
            }
        }
    }

    public Map<String, ContactHandler> getContactHandlerMap()
    {
        return contactHandlerMap;
    }

    public ObservableList<String> getObservableContactList()
    {
        return observableContactList;
    }

}