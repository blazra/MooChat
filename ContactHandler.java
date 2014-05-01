import java.net.*;
import java.io.*;

import javafx.application.Platform;
 
public class ContactHandler extends Thread
{
    private static enum State
    {
    WAITING, WAITFORNICK, READY;
    }

    private State state = State.WAITING;
    private String nick = "";

    private PrintWriter out;
    private BufferedReader in;

    ContactHandler(PrintWriter out, BufferedReader in, String nick)
    {
        this.out = out;
        this.in = in;
        this.nick = nick;
    }

    public void run()
    {
        String inputLine, outputLine;

        try
        {
            while((inputLine = in.readLine()) != null)
            {
                outputLine = processInput(inputLine);
                //out.println(outputLine);
                if(outputLine.equals("cus"))
                    break;
            }
        }
        catch(IOException e)
        {
            System.out.println("Exception caught.");
            System.out.println(e.getMessage());
        }
    }
 
    public String processInput(String theInput)
    { 
        String theOutput;
       
        if(theInput.equalsIgnoreCase(""))
            theOutput = "cus";
        else if(theInput.equalsIgnoreCase("hate doge"))
        {
            Platform.runLater(() -> Ui.getController().superDog.setVisible(true));
            theOutput = "OK";
        }
        else
        {
            Platform.runLater(() -> Ui.getController().showMsg(nick, theInput));
            theOutput = "OK";
        }
        
        return theOutput;
    }

    public void sendMsg(String msg)
    {
        out.println(msg);
    }
}