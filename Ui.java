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

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

import java.net.URL;


public class Ui extends Application {

    static Controller controller;
    static String nick = "blazra";              //TODO: @hardcoded nick
    static Server server;

    public static void main(String[] args)
    {
        String mode;                            //TODO: refine args processingu
        if(args.length == 0)
            mode = null;
        else
            mode = args[0];
        
        server = new Server(3333, mode);
        server.start();
        Application.launch(Ui.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage)
    {
        try {

            URL location = getClass().getResource("ui.fxml");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            VBox page = (VBox) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(page);

            controller = (Controller) fxmlLoader.getController();

            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if(ke.getCode() == KeyCode.ESCAPE){
                        Platform.exit();
                    }
                }
            });

            primaryStage.setScene(scene);
            primaryStage.setTitle("MooChat");
            primaryStage.getIcons().add(new Image("file:icon.png"));
            primaryStage.show();

        } catch (Exception ex)
        {
            Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
