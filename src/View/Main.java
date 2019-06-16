package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.util.Optional;


/* TODO:
        SolutionDisplayer--------------------------------- :)
        "Try Again" Button-------------------------------- :)
        Code MenuBar(New,Save,Load)----------------------- :) (*Load)
        Style
        fix resize problem-------------------------------- :)
        zoom in/out
        Music--------------------------------------------- :) (little bug)
        Log4j
        **JPG pictures
        Not-Square maze bug------------------------------- :)
        Winner-------------------------------------------- :)
        Finish Menu
        Maximize------------------------------------------ :)
 */
public class Main extends Application {

    public static MyModel model;

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Model
        model = new MyModel();

        // ViewMode
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        // Loading first window
        FXMLLoader myViewFXMLLoader = new FXMLLoader();
        Parent myViewRoot = myViewFXMLLoader.load(getClass().getResource("MyView.fxml").openStream());
        primaryStage.setTitle("Mazes");
        Scene scene = new Scene(myViewRoot);
        primaryStage.setMaximized(true);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);

        // View
        MyViewController myViewController = myViewFXMLLoader.getController();
        myViewController.initialize(primaryStage,viewModel);

        //Icon Image
        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("resources\\Icon.png")));
        }catch (Exception e){e.printStackTrace();}

        SetStageCloseEvent(primaryStage);
        primaryStage.show();

    }
    public static void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you want to quit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    // Close program
                    model.stopServers();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
