package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {


    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o==model){
            setChanged();
            notifyObservers(arg);
        }
    }

    public void generateMaze(int height, int width){
        model.generateMaze(height, width);
    }

    public void moveCharacter(KeyCode movement){
        model.moveCharacter(movement);
    }

    public Maze getMaze() {
        return model.getMaze();
    }

    public int getCharacterPositionRow() {
        //return characterPositionRowIndex;
        return model.getCharacterPositionRow();
    }

    public int getCharacterPositionColumn() {
        //return characterPositionColumnIndex;
        return model.getCharacterPositionColumn();
    }
    public void resetPlayerPosition(){
        model.resetPlayerPosition();
    }
    public void setMaze(Maze maze){
        model.setMaze(maze);
    }
    public void setCharacterPositionRow(int row){
        model.setCharacterPositionRow(row);

    }
    public void setCharacterPositionColumn(int column){
        model.setCharacterPositionColumn(column);
    }
    public void solveMaze(){
        model.solveMaze();
    }
    public Solution getSolution(){
        return model.getSolution();
    }
    public void stopServers(){
        model.stopServers();
    }
}
