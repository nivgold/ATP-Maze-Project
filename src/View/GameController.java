package View;


import java.util.Observable;
import java.util.Observer;

public class GameController implements IView, Observer {

    private MyViewController viewController;
    private MazeMenuController mazeMenuController;




    @Override
    public void displayMaze(int[][] maze) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
