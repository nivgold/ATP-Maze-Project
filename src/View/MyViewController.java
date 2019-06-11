package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {

    @FXML
    public javafx.scene.control.Button character_left;
    public javafx.scene.control.Button character_middle;
    public javafx.scene.control.Button character_right;
    public javafx.scene.control.Button button_Next;


    public boolean choosen = false;
    public String choosenPlayer="";

    public void left_onClicked(){
        character_left.setPrefWidth(200);
        character_left.setPrefHeight(240);
        character_middle.setPrefHeight(120);
        character_middle.setPrefWidth(100);
        character_right.setPrefWidth(100);
        character_right.setPrefHeight(120);
        choosen=true;
        choosenPlayer="ariel.jpg";
    }
    public void middle_onClicked(){
        character_left.setPrefWidth(100);
        character_left.setPrefHeight(120);
        character_middle.setPrefHeight(240);
        character_middle.setPrefWidth(200);
        character_right.setPrefWidth(100);
        character_right.setPrefHeight(120);
        choosen=true;
        choosenPlayer="armin.jpg";
    }

    public void right_onClicked(){
        character_left.setPrefWidth(100);
        character_left.setPrefHeight(120);
        character_middle.setPrefHeight(120);
        character_middle.setPrefWidth(100);
        character_right.setPrefWidth(200);
        character_right.setPrefHeight(240);
        choosen=true;
        choosenPlayer="meir.jpg";
    }

    @Override
    public void displayMaze(int[][] maze){

    }



    public void next_Clicked(ActionEvent event){
        if(choosen){
            try {
                Parent mazeMenu = FXMLLoader.load(getClass().getResource("MazeMenu.fxml"));
                Scene mazeMenuScene = new Scene(mazeMenu);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(mazeMenuScene);
                window.show();
            }
            catch (IOException e){e.printStackTrace();}
        }
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    /*
    public void initialize(Stage mainStage, Scene mainScene){
        this.mainScene=mainScene;
        this.mainScene=mainScene;
        button_Next.setOnAction(e -> About(e));
    }

     */
}
