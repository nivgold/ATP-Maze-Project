package View;


import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class GameController implements IView, Observer {

    private Scene mainScene;
    private Stage mainStage;
    private MyViewModel myViewModel;
    private String character;
    private String player_Name;
    private int maze_Rows;
    private int maze_Columns;
    private MediaPlayer mediaPlayer;

    private DoubleProperty myScale = new SimpleDoubleProperty(1.0);
    private Rectangle2D primary = Screen.getPrimary().getVisualBounds();

    @FXML
    public MazeDisplayer mazeDisplayer;
    public CharacterDisplayer characterDisplayer;
    public SolutionDisplayer solutionDisplayer;
    public javafx.scene.layout.Pane center_Pane;
    public javafx.scene.control.MenuBar menuBar;
    public javafx.scene.control.RadioMenuItem menu_Options_Mute;
    public javafx.scene.control.ToggleButton button_Solution;
    public javafx.scene.control.Button button_TryAgain;
    public javafx.scene.image.ImageView winner;
    public javafx.scene.image.ImageView winner2;
    public javafx.scene.control.MenuItem menu_File_Save;
    public javafx.scene.control.Menu menu_Exit;



    @Override
    public void displayMaze(int[][] maze) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Maze){
            mazeDisplayer.setMaze((Maze)arg);
        }
        else if(arg instanceof KeyCode){
            mazeDisplayer.drawMaze();
            characterDisplayer.setPosition(myViewModel.getCharacterPositionRow(), myViewModel.getCharacterPositionColumn());
            characterDisplayer.drawCharacter((KeyCode) arg);
        }
        else if(arg instanceof Solution){
            solutionDisplayer.setSolution((Solution)arg);
        }
        else if(arg instanceof Integer){
            try {
                button_Solution.setDisable(true);
                menu_File_Save.setDisable(true);
                this.winner2.fitWidthProperty().bind(center_Pane.widthProperty());
                this.winner2.fitHeightProperty().bind(center_Pane.heightProperty().divide(3));
                this.winner.fitHeightProperty().bind(center_Pane.heightProperty());
                this.winner.fitWidthProperty().bind(center_Pane.widthProperty());
                Image i2 = new Image(new FileInputStream("resources\\winner2.gif"));
                Image i = new Image(new FileInputStream("resources\\winner.gif"));
                this.winner.setImage(i);
                this.winner2.setImage(i2);
                this.winner.setVisible(true);
                this.winner2.setVisible(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void mute_Clicked(){
        if(menu_Options_Mute.isSelected()){
            mediaPlayer.stop();
        }
        else{
            mediaPlayer.play();
        }
    }

    public void initialize(Stage mainStage,Scene scene,MyViewModel viewModel, String character, String player_Name, int rows, int columns){
        this.mainScene=scene;
        this.mainStage=mainStage;
        myViewModel=viewModel;
        this.character=character;
        this.player_Name=player_Name;
        this.maze_Rows=rows;
        this.maze_Columns=columns;
        viewModel.addObserver(this);
        //bind-mazeDisplayer
        if(myViewModel.getMaze()==null)
            viewModel.generateMaze(rows,columns);
        bindMazeDisplayer();
        mazeDisplayer.setMaze(myViewModel.getMaze());
        //bind-CharacterDisplayer
        bindCharacterDisplayer();
        characterDisplayer.setCharacter(viewModel.getCharacterPositionRow(),viewModel.getCharacterPositionColumn(),character);
        //bind-SolutionDisplayer
        bindSolutionDisplayer();
        myViewModel.solveMaze();
        //Events
        setResizeEvent();
        set_Menu_Exit();
        //MediaPlayer
        String song2 = "resources\\Song2.mp3";
        Media media = new Media(new File(song2).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.02);
        mediaPlayer.setAutoPlay(true);

        center_Pane.scaleXProperty().bind(myScale);
        center_Pane.scaleYProperty().bind(myScale);
    }

    private void bindSolutionDisplayer(){
        solutionDisplayer.cellWidth.bind(center_Pane.widthProperty().divide(maze_Columns));
        solutionDisplayer.cellHeight.bind(center_Pane.heightProperty().divide(maze_Rows));
        solutionDisplayer.widthProperty().bind(mazeDisplayer.widthProperty());
        solutionDisplayer.heightProperty().bind(mazeDisplayer.heightProperty());
    }
    private void bindCharacterDisplayer() {
        characterDisplayer.cellWidth.bind(center_Pane.widthProperty().divide(maze_Columns));
        characterDisplayer.cellHeight.bind(center_Pane.heightProperty().divide(maze_Rows));
        characterDisplayer.widthProperty().bind(mazeDisplayer.widthProperty());
        characterDisplayer.heightProperty().bind(mazeDisplayer.heightProperty());
    }


    public void setResizeEvent() {
        center_Pane.widthProperty().addListener((observable, oldValue, newValue) -> {

            characterDisplayer.drawCharacter();
            mazeDisplayer.drawMaze();
        });
        center_Pane.heightProperty().addListener((observable, oldValue, newValue) -> {
            characterDisplayer.drawCharacter();
            mazeDisplayer.drawMaze();
        });
    }

    public void KeyPressed(KeyEvent keyEvent) {
        myViewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    public void file_New_Clicked(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",
                ButtonType.NO,
                ButtonType.YES);
        alert.setHeaderText("Are you sure you want to create a new maze?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            // ... user chose OK
            // Close program
            try {
                mediaPlayer.stop();
                FXMLLoader myViewFXMLLoader = new FXMLLoader();
                Parent myViewRoot = myViewFXMLLoader.load(getClass().getResource("MyView.fxml").openStream());
                Scene MyViewScene = new Scene(myViewRoot);
                MyViewScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
                this.mainStage.setScene(MyViewScene);
                mainStage.setX(primary.getMinX());
                mainStage.setY(primary.getMinY());
                mainStage.setWidth(primary.getWidth());
                mainStage.setHeight(primary.getHeight());
                myViewModel.resetPlayerPosition();
                myViewModel.setMaze(null);
                // Transferring Data
                MyViewController myViewController = myViewFXMLLoader.getController();
                myViewController.initialize(mainStage,myViewModel);
            }catch (IOException e){e.printStackTrace();}
        }
        else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public void file_Save_Clicked(){
        FileChooser fileChooser = new FileChooser();
        //Extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(null);
        if(file!=null){
            saveMazeToDisk(file);
        }
    }

    private void saveMazeToDisk(File file) {
        try(ObjectOutputStream ob = new ObjectOutputStream(new FileOutputStream(file))){
            ob.writeObject(player_Name);
            ob.writeObject(character);
            ob.writeObject(maze_Rows);
            ob.writeObject(maze_Columns);
            ob.writeObject(myViewModel.getMaze());
            ob.writeObject(myViewModel.getCharacterPositionRow());
            ob.writeObject(myViewModel.getCharacterPositionColumn());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void file_Load_Clicked(){
        FileChooser fileChooser = new FileChooser();
        //Extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);

        if(file!=null) {
            loadMazeFromDisk(file);
            bindMazeDisplayer();
            mazeDisplayer.setMaze(myViewModel.getMaze());
            bindCharacterDisplayer();
            characterDisplayer.setCharacter(myViewModel.getCharacterPositionRow(),myViewModel.getCharacterPositionColumn(),character);
        }
    }

    private void bindMazeDisplayer() {
        mazeDisplayer.widthProperty().bind(center_Pane.widthProperty());
        mazeDisplayer.heightProperty().bind(center_Pane.heightProperty());
        mazeDisplayer.cellHeight.bind(center_Pane.heightProperty().divide(maze_Rows));
        mazeDisplayer.cellWidth.bind(center_Pane.widthProperty().divide(maze_Columns));
    }

    private void loadMazeFromDisk(File file) {
        try(ObjectInputStream ob = new ObjectInputStream(new FileInputStream(file))){
            this.player_Name = (String)ob.readObject();
            this.character = (String)ob.readObject();
            this.maze_Rows = (Integer)ob.readObject();
            this.maze_Columns = (Integer)ob.readObject();
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
    }

    public void try_Again_Clicked(){
        //MediaPlayer
        String path = "resources\\blip.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.02);
        mediaPlayer.setAutoPlay(true);

        //Style
        tryAgain_onMouseClicked_Style();

        myViewModel.resetPlayerPosition();
        characterDisplayer.setPosition(myViewModel.getCharacterPositionRow(),myViewModel.getCharacterPositionColumn());
        characterDisplayer.drawCharacter(KeyCode.NUMPAD2);
        solutionDisplayer.eraseSolution();
        this.winner.setVisible(false);
        this.winner2.setVisible(false);
        menu_File_Save.setDisable(false);
        button_Solution.setDisable(false);
    }

    public void solve_Clicked(){
        String path = "resources\\blip.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.02);
        mediaPlayer.setAutoPlay(true);
        if(button_Solution.isSelected()) {
            button_Solution.setStyle("-fx-effect: innershadow( three-pass-box, rgba(0, 0, 0, 0.4),5,0.0,5,5);" +
                    "-fx-background-color:" +
                    "linear-gradient(#f0ff35, #a9ff00)," +
                    "radial-gradient(center 50% -40%, radius 200%, #85b934 45%, #71b900 50%);");
            solutionDisplayer.drawSolution();
        }
        else{
            solutionDisplayer.eraseSolution();
            set_MouseEntered_Style(button_Solution);
        }
    }

    public void handle(ScrollEvent event){
        double delta = 1.2;


        double scale = myScale.get(); // currently we only use Y, same value is used for X
        double oldScale = scale;

        if (event.getDeltaY() < 0)
            scale /= delta;
        else
            scale *= delta;

        scale = clamp( scale, .1d, 10.0d);

        double f = (scale / oldScale)-1;

        double dx = (event.getSceneX() - (center_Pane.getBoundsInParent().getWidth()/2 + center_Pane.getBoundsInParent().getMinX()));
        double dy = (event.getSceneY() - (center_Pane.getBoundsInParent().getHeight()/2 + center_Pane.getBoundsInParent().getMinY()));

        myScale.set(scale);

        // note: pivot value must be untransformed, i. e. without scaling
        center_Pane.setTranslateX(center_Pane.getTranslateX()-f*dx);
        center_Pane.setTranslateY(center_Pane.getTranslateY()-f*dy);

        event.consume();
    }

    public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
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

    public void solution_OnMouseEntered(){
        if(!button_Solution.isSelected())
            set_MouseEntered_Style(button_Solution);
    }

    public void solution_OnMouseExit(){
        if(!button_Solution.isSelected())
            set_MouseExit_Style(this.button_Solution);
    }
    public void tryAgain_OnMouseEntered(){
        set_MouseEntered_Style(button_TryAgain);
    }
    public void tryAgain_OnMouseExit(){
        set_MouseExit_Style(button_TryAgain);
    }

    private void set_MouseEntered_Style(Control control){
        control.setStyle("-fx-background-color: " +
                "linear-gradient(#f0ff35, #a9ff00)," +
                "radial-gradient(center 50% -40%, radius 200%, #c5ee73 45%, #90d700 50%);" +
                "-fx-background-radius: 6, 5;" +
                "-fx-background-insets: 0, 1;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );" +
                "-fx-text-fill: #395306;");
    }

    private void set_MouseExit_Style(Control control){
        control.setStyle("-fx-background-color: " +
                "linear-gradient(#f0ff35, #a9ff00)," +
                "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);" +
                "-fx-background-radius: 6, 5;" +
                "-fx-background-insets: 0, 1;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );" +
                "-fx-text-fill: #395306;");
    }



    public void tryAgain_onMouseClicked_Style(){
        button_TryAgain.setStyle("-fx-effect: innershadow( three-pass-box, rgba(0, 0, 0, 0.4),5,0.0,5,5);" +
                "-fx-background-color:" +
                "linear-gradient(#f0ff35, #a9ff00)," +
                "radial-gradient(center 50% -40%, radius 200%, #85b934 45%, #71b900 50%);");
    }






}
