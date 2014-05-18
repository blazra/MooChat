import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalTime;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button btn;

    @FXML
    private Label serverLbl;

    @FXML
    private TextField serverTextField;

    @FXML
    private Label portLbl;

    @FXML
    private TextField portTextField;

    @FXML
    private Label nickLbl;

    @FXML
    private TextField nickTextField;

    @FXML
    private ListView<String> contactsView;

    LocalTime time;

	@Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {

        contactsView.setItems(Ui.getServer().getObservableContactList());

        typingArea.setOnKeyPressed( (event) -> {
            final KeyCombination enterCombo = new KeyCodeCombination(KeyCode.ENTER);
            if(enterCombo.match(event))
            {
                event.consume();
                showMsg(Ui.nick, typingArea.getText());
                Ui.getServer().getContactHandlerMap().get("blazra").sendMsg(typingArea.getText());
                typingArea.clear();
            }           
        });

        btn.setOnAction( (event) -> {
            if(Ui.getStage().getHeight() > 240)
            {
                nickLbl.setVisible(false);
                nickTextField.setVisible(false);
                serverLbl.setVisible(false);
                serverTextField.setVisible(false);
                portLbl.setVisible(false);
                portTextField.setVisible(false);
                Ui.getStage().setHeight(240);
            }
            else
            {
                nickLbl.setVisible(true);
                nickTextField.setVisible(true);
                serverLbl.setVisible(true);
                serverTextField.setVisible(true);
                portLbl.setVisible(true);
                portTextField.setVisible(true);
                Ui.getStage().setHeight(350);
            }
        });
    }

    public void showMsg(String nick, String msg)
    {
        time = LocalTime.now();
        messageArea.appendText("(" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + ") "
            + nick + ": " + msg + "\n");            // TODO: clock format "1:1:1" to "1:01:01"
    }

}
