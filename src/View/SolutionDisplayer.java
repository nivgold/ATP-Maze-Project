package View;


import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class SolutionDisplayer extends Canvas {

    Solution solution;
    public DoubleProperty cellHeight = new SimpleDoubleProperty(1);
    public DoubleProperty cellWidth = new SimpleDoubleProperty(1);

    public void setSolution(Solution solution){
        this.solution=solution;
    }

    public void drawSolution(){
        try {
            Image solImage = new Image(new FileInputStream("resources\\Game\\solution.jpg"));
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());
            ArrayList<AState> path = solution.getSolutionPath();
            for (int i = 0; i < path.size(); i++) {
                String s = path.get(i).toString();
                String[] data = s.split(",");
                int row = Integer.parseInt(data[0].substring(1));
                int col = Integer.parseInt(data[1].substring(0,data[1].length()-1));
                gc.drawImage(solImage,col*cellWidth.getValue(),row*cellHeight.getValue(),cellWidth.getValue(),cellHeight.getValue());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void eraseSolution(){
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0,2000,2000);
    }
}
