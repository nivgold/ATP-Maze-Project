package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MazeMenuController{

    @FXML
    public javafx.scene.control.TextField textField_MazeRows;
    public javafx.scene.control.TextField textField_MazeColumns;
    public javafx.scene.control.TextField textField_PlayerName;
    public javafx.scene.control.Menu menu_Exit;
    public javafx.scene.control.CheckMenuItem BestFirstSearch;
    public javafx.scene.control.CheckMenuItem BreathFirstSearch;
    public javafx.scene.control.CheckMenuItem DepthFirstSearch;
    public javafx.scene.control.CheckMenuItem EmptyMazeGenerator;
    public javafx.scene.control.CheckMenuItem RandomMazeGenerator;
    public javafx.scene.control.CheckMenuItem MyMazeGenerator;
    public javafx.scene.control.MenuItem thread_Pool;
    public javafx.scene.control.Button button_Play;

    private boolean play;
    private String characterName;
    private MyViewModel myViewModel;
    private MediaPlayer mediaPlayer;
    private Stage mainStage;
    private Rectangle2D primary = Screen.getPrimary().getVisualBounds();

    public void play_Clicked(ActionEvent actionEvent) {
        String path = "resources\\blip.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer2 = new MediaPlayer(media);
        mediaPlayer2.setVolume(0.02);
        mediaPlayer2.setAutoPlay(true);
        play = true;
        if(!check(textField_MazeRows)) {
            textField_MazeRows.setStyle("-fx-border-color: #ff381a;"+
                    "-fx-border-width: 2.5;" +
                    "-fx-background-image: url(file:resources/warning.png);" +
                    "-fx-background-position: right;" +
                    "-fx-background-size: 40px 40px;" +
                    "-fx-background-repeat: no-repeat");
            play=false;
        }
        else {
            textField_MazeRows.setStyle("-fx-background-color: linear-gradient(to bottom, derive(-fx-text-box-border, -10%), -fx-text-box-border),\n" +
                    "linear-gradient(from 0px 0px to 0px 5px, derive(-fx-control-inner-background, -9%), -fx-control-inner-background)");
        }
        if(!check(textField_MazeColumns)) {
            textField_MazeColumns.setStyle("-fx-border-color: #ff381a;"+
                    "-fx-border-width: 2.5;" +
                    "-fx-background-image: url(file:resources/warning.png);" +
                    "-fx-background-position: right;" +
                    "-fx-background-size: 40px 40px;" +
                    "-fx-background-repeat: no-repeat");
            play=false;
        }
        else {
            textField_MazeColumns.setStyle("-fx-background-color: linear-gradient(to bottom, derive(-fx-text-box-border, -10%), -fx-text-box-border),\n" +
                    "    linear-gradient(from 0px 0px to 0px 5px, derive(-fx-control-inner-background, -9%), -fx-control-inner-background)");

        }
        if(!check_Name()){
            textField_PlayerName.setStyle("-fx-border-color: #ff381a;"+
                    "-fx-border-width: 2.5;" +
                    "-fx-background-image: url(file:resources/warning.png);" +
                    "-fx-background-position: right;" +
                    "-fx-background-size: 40px 40px;" +
                    "-fx-background-repeat: no-repeat");
            play=false;
        }
        else {
            textField_PlayerName.setStyle("-fx-background-color: linear-gradient(to bottom, derive(-fx-text-box-border, -10%), -fx-text-box-border),\n" +
                    "    linear-gradient(from 0px 0px to 0px 5px, derive(-fx-control-inner-background, -9%), -fx-control-inner-background)");

        }

        if(play){
            try {
                FXMLLoader gameFXMLLoader = new FXMLLoader();
                Parent mazeMenu = gameFXMLLoader.load(getClass().getResource("Game.fxml").openStream());
                Scene gameScene = new Scene(mazeMenu);
                gameScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
                mainStage.setScene(gameScene);
                mainStage.setX(primary.getMinX());
                mainStage.setY(primary.getMinY());
                mainStage.setWidth(primary.getWidth());
                mainStage.setHeight(primary.getHeight());
                // Transferring Data
                GameController gameController = gameFXMLLoader.getController();
                gameController.initialize(mainStage,gameScene,myViewModel,characterName,textField_PlayerName.getText(),Integer.parseInt(textField_MazeRows.getText()),Integer.parseInt(textField_MazeColumns.getText()));
                mediaPlayer.stop();
            }
            catch (IOException e){e.printStackTrace();}
        }

    }
    private boolean check(javafx.scene.control.TextField textField){
        String data = textField.getText();
        if(data.length()==0)
            return false;
        for (int i=0; i<data.length(); i++){
            if(data.charAt(i)<'0' || data.charAt(i)>'9')
                return false;
        }
        return true;
    }
    private boolean check_Name(){
        String data = textField_PlayerName.getText();
        if(data.length()==0)
            return false;
        return true;
    }

    public void initialize(Stage mainStage,MediaPlayer mediaPlayer,String character_Name, MyViewModel viewModel){
        mainStage.setMaximized(true);
        this.mainStage=mainStage;
        this.characterName = character_Name;
        this.myViewModel = viewModel;
        this.mediaPlayer=mediaPlayer;
        field();
        set_Menu_Exit();
    }

    private void set_Menu_Exit(){
        Label exit = new Label("Exit");
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you want to quit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    // Close program
                    myViewModel.stopServers();
                    mainStage.close();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    event.consume();
                }
            }
        });
        menu_Exit.setGraphic(exit);
    }
    private void field(){
        TextField textField = new TextField();
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Configurations.setPoolNumber(Integer.parseInt(textField.getText()));
            }
        });
        thread_Pool.setGraphic(textField);
    }


    public void solver_BestFirstSearch(){
        BreathFirstSearch.setSelected(false);
        DepthFirstSearch.setSelected(false);
        Configurations.setSolver("BestFirstSearch");
    }
    public void solver_BreadthFirstSearch(){
        BestFirstSearch.setSelected(false);
        DepthFirstSearch.setSelected(false);
        Configurations.setSolver("BreadthFirstSearch");
    }
    public void solver_DepthFirstSearch(){
        BestFirstSearch.setSelected(false);
        DepthFirstSearch.setSelected(false);
        Configurations.setSolver("DepthFirstSearch");
    }
    public void generator_EmptyMazeGenerator(){
        RandomMazeGenerator.setSelected(false);
        MyMazeGenerator.setSelected(false);
        Configurations.setGenerator("EmptyMazeGenerator");
    }
    public void generator_SimpleMazeGenerator(){
        EmptyMazeGenerator.setSelected(false);
        MyMazeGenerator.setSelected(false);
        Configurations.setGenerator("SimpleMazeGenerator");
    }
    public void generator_MyMazeGenerator(){
        EmptyMazeGenerator.setSelected(false);
        RandomMazeGenerator.setSelected(false);
        Configurations.setGenerator("MyMazeGenerator");
    }

    public void play_OnMouseEntered(){
        button_Play.setStyle("-fx-background-color: linear-gradient(#fffa9b, #e68400)," +
                "linear-gradient(#ffef84, #f2e46b)," +
                "linear-gradient(#fffda7, #efd549)," +
                "linear-gradient(#fffaa9 0%, #f8e625 50%, #eedd3e 100%)," +
                "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.5), rgba(255,255,255,0));" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 24px;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-pref-width: 250;" +
                "-fx-pref-height: 75;");
    }

    public void play_OnMouseExit(){
        button_Play.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400)," +
                "linear-gradient(#ffef84, #f2ba44)," +
                "linear-gradient(#ffea6a, #efaa22)," +
                "linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%)," +
                "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: #654b00;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 24px;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-pref-width: 250;" +
                "-fx-pref-height: 75;");
    }



}
