import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.prefs.Preferences;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

import java.net.URL;


public class Ui extends Application {

    private static Controller controller;
    private static String nick = "blazra";              //TODO: @hardcoded nick
    private static Server server;
    private static Client client;
    private static Stage primaryStage;
    private static Preferences prefs;

    public static void main(String[] args)
    {
        prefs = Preferences.userRoot().node("MooChat");

        if(args.length != 0)
            if(args[0].equals("s"))
            {
                server = new Server(5000);
                server.setDaemon(true);
                server.start();
            }
            else
            {
                System.out.println("Unknown parameter - use \"s\" for server mode");
            }
        else
        {
            client = new Client(prefs.get("server_URL", "localhost") ,prefs.getInt("server_port", 5000));
            client.setDaemon(true);
            client.start();
            Application.launch(Ui.class, (java.lang.String[])null);
        }
    }

    @Override
    public void start(Stage primaryStage)
    {
        try {

            Ui.primaryStage = primaryStage;
            URL location = getClass().getResource("ui.fxml");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            VBox page = (VBox) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(page);

            controller = (Controller)fxmlLoader.getController();

            scene.setOnKeyPressed( (ke) -> {
                if(ke.getCode() == KeyCode.ESCAPE){
                    Platform.exit();
                }
            });

            primaryStage.setScene(scene);
            primaryStage.setTitle("MooChat");
            primaryStage.getIcons().add(new Image("file:icon.png"));
            primaryStage.setResizable(false);
            primaryStage.setHeight(240);
            primaryStage.show();

        } catch (Exception ex)
        {
            Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Stage getStage()
    {
        return primaryStage;
    }

    public static Controller getController()
    {
        return controller;
    }

    public static String getNick()
    {
        return nick;
    }

    public static Server getServer()
    {
        return server;
    }

    public static Client getClient()
    {
        return client;
    }
}
