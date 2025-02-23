import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/spongebob.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/patrick.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Helper method to be used in Main.
     *
     * @param d Duke object
     */
    public void setDuke(Duke d) {
        DialogBox startBox = DialogBox.getDukeDialog(d.getStartMessage(), dukeImage);
        dialogContainer.getChildren().add(startBox);
        duke = d;

    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Duke's reply and then appends them to the dialog container. Clears the user
     * input after processing.
     */
    @FXML
    private void handleUserInput() throws IOException {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.setSpacing(5);
        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage));
        userInput.clear();

        if (input.equals("bye")) {
            System.exit(0);
        }
    }
}