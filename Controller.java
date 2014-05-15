import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    public ImageView superDog;

    @FXML
    private TextArea typingArea;

    @FXML
    private TextArea messageArea;

    @FXML
    private ListView<String> contactsView;

    LocalTime time;

	@Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {

        contactsView = new ListView<String>(Ui.getServer().getObservableContactList());

        typingArea.setOnKeyReleased(new EventHandler<KeyEvent>() {

            final KeyCombination enterCombo = new KeyCodeCombination(KeyCode.ENTER);

            public void handle(KeyEvent event)
            {
                if(enterCombo.match(event))
                {
                    typingArea.deletePreviousChar();

                    messageArea.appendText(typingArea.getText() + "\n");
                    Ui.getServer().getContactHandlerMap().get("blazra").sendMsg(typingArea.getText());
                    typingArea.clear();
                }
            }
        });
    }

    public void showMsg(String nick, String msg)
    {
        time = LocalTime.now();
        messageArea.appendText("(" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + ") "
            + nick + ": " + msg + "\n");
    }

}
