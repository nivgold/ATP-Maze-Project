package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MazeMenuController{

    @FXML
    public javafx.scene.control.TextField textField_MazeRows;
    public javafx.scene.control.TextField textField_MazeColumns;
    public javafx.scene.control.TextField textField_PlayerName;

    boolean play;

    public void play_Clicked(ActionEvent actionEvent) {
        play = true;
        if(!check(textField_MazeRows)) {
            textField_MazeRows.setStyle("-fx-border-color: red;"+
                    "-fx-background-color: #ff9984;"+
                    "-fx-border-width: 2");
            play=false;
        }
        else {
            textField_MazeRows.setStyle("-fx-background-color: linear-gradient(to bottom, derive(-fx-text-box-border, -10%), -fx-text-box-border),\n" +
                    "    linear-gradient(from 0px 0px to 0px 5px, derive(-fx-control-inner-background, -9%), -fx-control-inner-background)");
        }
        if(!check(textField_MazeColumns)) {
            textField_MazeColumns.setStyle("-fx-border-color: red;"+
                    "-fx-background-color: #ff9984;"+
                    "-fx-border-width: 2");
            play=false;
        }
        else {
            textField_MazeColumns.setStyle("-fx-background-color: linear-gradient(to bottom, derive(-fx-text-box-border, -10%), -fx-text-box-border),\n" +
                    "    linear-gradient(from 0px 0px to 0px 5px, derive(-fx-control-inner-background, -9%), -fx-control-inner-background)");

        }
        if(!check_Name()){
            textField_PlayerName.setStyle("-fx-border-color: red;"+
                    "-fx-background-color: #ff9984;"+
                    "-fx-border-width: 2");
            play=false;
        }
        else {
            textField_PlayerName.setStyle("-fx-background-color: linear-gradient(to bottom, derive(-fx-text-box-border, -10%), -fx-text-box-border),\n" +
                    "    linear-gradient(from 0px 0px to 0px 5px, derive(-fx-control-inner-background, -9%), -fx-control-inner-background)");

        }

        if(play){
            try {
                Parent mazeMenu = FXMLLoader.load(getClass().getResource("Game.fxml"));
                Scene mazeMenuScene = new Scene(mazeMenu);
                Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                window.setScene(mazeMenuScene);
                window.show();
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

}
