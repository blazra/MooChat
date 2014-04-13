import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class Controller implements Initializable {

    @FXML
    private VBox topVbox;

    @FXML
    private AnchorPane topAnchorPane;

    @FXML
    private ImageView superDog;

    @FXML
    private TextArea typingArea;

    @FXML
    private TextArea messageArea;

    @FXML
    private ListView<?> contactsView;

	@Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        
        typingArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            final KeyCombination enterCombo = new KeyCodeCombination(KeyCode.ENTER);
            final KeyCombination colonCombo = new KeyCodeCombination(KeyCode.COLON);
            public void handle(KeyEvent event)
            {
                if(enterCombo.match(event))
                {
                    messageArea.setText(messageArea.getText()+" enter");
                }
                else if(colonCombo.match(event))
                {
                    messageArea.setStyle("-fx-highlight-fill: lightgray;");
                    System.out.println("color set");
                }
            }
        });

    }

}
