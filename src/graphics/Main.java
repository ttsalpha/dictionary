package graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import terminal.DictionaryManagement;

public class Main extends Application {

    public static Stage mainStage = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("> Start");
        Parent root = FXMLLoader.load(getClass().getResource("view/Search.fxml"));
        System.out.println("> Search window has loaded");
        primaryStage.setScene(new Scene(root));

        DictionaryManagement.insertFromFile();
        System.out.println("> Insert from file successfully");

        mainStage = primaryStage;

        primaryStage.setTitle("Dictionary from Son Tran");
        primaryStage.show();
    }

}
