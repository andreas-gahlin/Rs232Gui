package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @version 1.0 28 Sep 2015
 * @author Andreas Gåhlin
 * @email andreas.gahlin@gmail.com
 */

public class LoginController {
    /**
     * FUNCTION NAME : readSerial
     * DESCRIPTION   : Shall always read the buffer and try to receive data from the H/W because we will never know when
     *                 data is going to be available
     * INPUT         : Data from the serial cable who was sent from the H/W
     * OUTPUT        : Print out the data from the H/W to the view(JFrame)
     * NOTE          : -
     */

    @FXML private Text ActionTarget;
    @FXML private TextField userNameField, passwordField;

    @FXML protected void handleSubmitButtonLogin(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        if(!userName.equals("") && !password.equals("")) {
            String ans = new DBHandler().httpRequestPost(userName, password, ""); // UserRegistration.php
            if(ans.equals("true")){
                openWindow(event);
            }
            else
                ActionTarget.setText(ans);
        }
        else {
            openWindow(event);
        }
    }

    @FXML protected void handleSubmitButtonExit(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void openWindow(ActionEvent event){
        try {
            Parent mediaView = FXMLLoader.load(getClass().getClassLoader().getResource("Windows/RSMenuView.fxml")); //RSControllerView.fxml
            Stage mediaStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mediaStage.setTitle("RS232 Controller");
            mediaStage.getIcons().add(new Image("/Icon/CRAZYCAT.png"));
            mediaStage.setResizable(false);
            mediaStage.setScene(new Scene(mediaView, 640, 400));
            mediaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
