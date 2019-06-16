package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.*;
import java.util.Optional;

public class MyViewController{

    @FXML
    public javafx.scene.control.Button character_left;
    public javafx.scene.control.Button character_middle;
    public javafx.scene.control.Button character_right;
    public javafx.scene.control.Button button_Next;
    public javafx.scene.control.MenuBar menuBar;
    public javafx.scene.control.Menu menu_Exit;
    public javafx.scene.control.CheckMenuItem BestFirstSearch;
    public javafx.scene.control.CheckMenuItem BreathFirstSearch;
    public javafx.scene.control.CheckMenuItem DepthFirstSearch;
    public javafx.scene.control.CheckMenuItem EmptyMazeGenerator;
    public javafx.scene.control.CheckMenuItem RandomMazeGenerator;
    public javafx.scene.control.CheckMenuItem MyMazeGenerator;
    public javafx.scene.control.MenuItem thread_Pool;
    public javafx.scene.control.Menu menu_About;
    public javafx.scene.control.Menu menu_Help;


    public boolean choosen = false;
    public String choosenPlayer="";
    private MyViewModel myViewModel;
    private MediaPlayer mediaPlayer;
    private Stage mainStage;
    private Rectangle2D primary = Screen.getPrimary().getVisualBounds();

    public void left_onClicked(){
        character_left.setPrefWidth(200);
        character_left.setPrefHeight(240);
        character_middle.setPrefHeight(120);
        character_middle.setPrefWidth(100);
        character_right.setPrefWidth(100);
        character_right.setPrefHeight(120);
        choosen=true;
        choosenPlayer="resources\\Characters\\Maplestory";
    }
    public void middle_onClicked(){
        character_left.setPrefWidth(100);
        character_left.setPrefHeight(120);
        character_middle.setPrefHeight(240);
        character_middle.setPrefWidth(200);
        character_right.setPrefWidth(100);
        character_right.setPrefHeight(120);
        choosen=true;
        choosenPlayer="resources\\Characters\\Troll";
    }

    public void right_onClicked(){
        character_left.setPrefWidth(100);
        character_left.setPrefHeight(120);
        character_middle.setPrefHeight(120);
        character_middle.setPrefWidth(100);
        character_right.setPrefWidth(200);
        character_right.setPrefHeight(240);
        choosen=true;
        choosenPlayer="resources\\Characters\\Mario";
    }



    public void next_Clicked(ActionEvent event){
        //MediaPlayer
        String path = "resources\\blip.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer2 = new MediaPlayer(media);
        mediaPlayer2.setVolume(0.02);
        mediaPlayer2.setAutoPlay(true);

        if(choosen){
            try {
                FXMLLoader mazeMenuFXMLLoader = new FXMLLoader();
                Parent mazeMenu = mazeMenuFXMLLoader.load(getClass().getResource("MazeMenu.fxml").openStream());
                Scene mazeMenuScene = new Scene(mazeMenu);
                mazeMenuScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
                mainStage.setScene(mazeMenuScene);
                mainStage.setX(primary.getMinX());
                mainStage.setY(primary.getMinY());
                mainStage.setWidth(primary.getWidth());
                mainStage.setHeight(primary.getHeight());
                // Transferring Data
                MazeMenuController mazeMenuController = mazeMenuFXMLLoader.getController();
                mazeMenuController.initialize(mainStage,mediaPlayer,choosenPlayer,myViewModel);
            }
            catch (IOException e){e.printStackTrace();}
        }
    }



    public void initialize(Stage mainStage,MyViewModel viewModel){
        this.mainStage=mainStage;
        this.myViewModel = viewModel;


        //MediaPlayer
        String path = "resources\\Song1.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.02);
        mediaPlayer.setAutoPlay(true);


        set_Menu_Exit();
        set_Menu_About();
        set_Menu_Help();
        field();
    }

    public void menu_Load_Clicked(){
        FileChooser fileChooser = new FileChooser();
        //Extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);
        try {
            FXMLLoader myViewFXMLLoader = new FXMLLoader();
            Parent gameRoot = myViewFXMLLoader.load(getClass().getResource("Game.fxml").openStream());
            Scene gameScene = new Scene(gameRoot);
            gameScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
            Stage window = (Stage) menuBar.getScene().getWindow();
            window.setScene(gameScene);
            // Transferring Data
            GameController gameController = myViewFXMLLoader.getController();

            // reading from file
            initializeFromFile(file,gameController,gameScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeFromFile(File file, GameController gameController,Scene gameScene) {
        String player_Name="",character="";
        int maze_Rows=0,maze_Columns=0;
        try(ObjectInputStream ob = new ObjectInputStream(new FileInputStream(file))){
            player_Name = (String)ob.readObject();
            character = (String)ob.readObject();
            maze_Rows = (Integer)ob.readObject();
            maze_Columns = (Integer)ob.readObject();
            myViewModel.setMaze((Maze)ob.readObject());
            myViewModel.setCharacterPositionRow((Integer)ob.readObject());
            myViewModel.setCharacterPositionColumn((Integer)ob.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        gameController.initialize(mainStage,gameScene,myViewModel,character,player_Name,maze_Rows,maze_Columns);

    }
    private void set_Menu_About(){
        Label label = new Label("About");
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Stage stage = new Stage();
                    stage.setTitle("About");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
                    Scene scene = new Scene(root, 580, 300);
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                    stage.getIcons().add(new Image(new FileInputStream("resources\\Icon.png")));
                    stage.setMaxWidth(580);
                    stage.setMaxHeight(300);
                    stage.setMinWidth(580);
                    stage.setMinHeight(300);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        menu_About.setGraphic(label);
    }
    private void set_Menu_Help(){
        Label label = new Label("Help");
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Stage stage = new Stage();
                    stage.setTitle("Help");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent root = fxmlLoader.load(getClass().getResource("Help.fxml").openStream());
                    Scene scene = new Scene(root, 600, 400);
                    stage.setScene(scene);
                    root.setStyle("-fx-background-color: #70c8a0");
                    stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                    stage.getIcons().add(new Image(new FileInputStream("resources\\Icon.png")));
                    stage.setMaxWidth(600);
                    stage.setMaxHeight(400);
                    stage.setMinWidth(600);
                    stage.setMinHeight(400);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        menu_Help.setGraphic(label);
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


    public void next_OnMouseEntered(){
        button_Next.setStyle("-fx-background-color: linear-gradient(#fffa9b, #e68400)," +
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
    public void next_OnMouseExit(){
        button_Next.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400)," +
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
