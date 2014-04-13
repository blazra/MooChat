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

public class Ui extends Application {

    public static void main(String[] args) {
        Application.launch(Ui.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            VBox page = (VBox) FXMLLoader.load(Ui.class.getResource("ui.fxml"));
            Scene scene = new Scene(page);
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
        } catch (Exception ex) {
            Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
