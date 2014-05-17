import java.net.*;
import java.io.*;
import java.util.*;
import javafx.collections.*;
 
public class Server extends Thread
{
    int portNumber;
    String connectionMode;

    private Map<String, ContactHandler> contactHandlerMap;
    private ObservableList<String> observableContactList;

    Server(int portNumber, String connectionMode)
    {
        this.portNumber = portNumber;
        if(connectionMode != null)
            this.connectionMode = connectionMode;
        else
            this.connectionMode = "";

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

                if(connectionMode.equalsIgnoreCase("c"))    //client mode
                    clientSocket = new Socket("localhost", portNumber);
                else
                {
                    ServerSocket serverSocket = new ServerSocket(portNumber);
                    clientSocket = serverSocket.accept();
                }

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine, outputLine;

                // Initiate conversation with client

                out.println(Ui.getNick());
                String nick = in.readLine();

                ContactHandler contactHandler = new ContactHandler(out, in, nick);
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