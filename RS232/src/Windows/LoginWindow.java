package Windows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @version 1.0 28 Sep 2015
 * @author Andreas Gåhlin
 * @email andreas.gahlin@gmail.com
 */

public class LoginWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        primaryStage.setTitle("CopyCat Media Player Login"); // CC = Crazy/CopyCat software
        primaryStage.getIcons().add(new Image("/Icon/CRAZYCAT.png"));
        primaryStage.setScene(new Scene(root, 460, 340));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
