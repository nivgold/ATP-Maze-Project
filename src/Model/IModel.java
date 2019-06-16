package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

public interface IModel {
    //Maze
    void generateMaze(int width, int height);
    Maze getMaze();

    //Character
    void moveCharacter(KeyCode movement);
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    void resetPlayerPosition();
    void setMaze(Maze maze);
    void setCharacterPositionRow(int row);
    void setCharacterPositionColumn(int column);
    void solveMaze();
    Solution getSolution();
    void stopServers();

}
