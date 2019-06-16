package View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {

    private Maze maze;

    public DoubleProperty cellHeight = new SimpleDoubleProperty(1);
    public DoubleProperty cellWidth = new SimpleDoubleProperty(1);

    public void setMaze(Maze maze) {
        this.maze = maze;
        drawMaze();
    }

    public void drawMaze() {
        int[][] mazeArray = maze.getM_Maze();


        try {
            Image wallImage = new Image(new FileInputStream("resources\\Game\\Grass.jpg"));
            Image pathImage = new Image(new FileInputStream("resources\\Game\\Klum.jpg"));
            Image endImage = new Image(new FileInputStream("resources\\Game\\target.jpg"));
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());
            //Draw Maze
            for (int i = 0; i < mazeArray.length; i++) {
                for (int j = 0; j < mazeArray[i].length; j++) {
                    if(i==mazeArray.length-1 && j == mazeArray[i].length-1){
                        gc.drawImage(endImage ,j*cellWidth.getValue(),i * cellHeight.getValue() , cellWidth.getValue(), cellHeight.getValue());
                    }
                    else if (mazeArray[i][j] == 1) {
                        //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                        gc.drawImage(wallImage, j*cellWidth.getValue(),i * cellHeight.getValue() , cellWidth.getValue(), cellHeight.getValue());
                    } else {
                        gc.drawImage(pathImage, j*cellWidth.getValue(), i * cellHeight.getValue(), cellWidth.getValue(), cellHeight.getValue());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void zoomIn(int characherRow, int characterColumn, int zoom){
        try {
            Image wallImage = new Image(new FileInputStream("resources\\Game\\Grass.jpg"));
            Image pathImage = new Image(new FileInputStream("resources\\Game\\Klum.jpg"));
            Image endImage = new Image(new FileInputStream("resources\\Game\\target.jpg"));
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());
            double height = getHeight() / (2 * zoom + 1);
            double width = getWidth() / (2 * zoom + 1);
            int printx = 0;
            int printy = 0;
            for (int i = characherRow - zoom; i <= characherRow + zoom; i++) {
                for (int j = characterColumn - zoom; j <= characterColumn + zoom; j++) {
                    if (i < 0 || j < 0 || i >= maze.getM_Rows() || j >= maze.getM_Columns())
                        gc.drawImage(pathImage,printy*width,printx*height,width,height);
                    else if(i==maze.getM_Maze().length-1 && j == maze.getM_Maze()[i].length-1) {
                        gc.drawImage(endImage,printy*width,printx*height,width,height);
                    }
                    else if (maze.getM_Maze()[i][j] == 1) {
                        gc.drawImage(wallImage, printy*width,printx * height , width, height);
                    }
                    else
                        gc.drawImage(pathImage, printy*width,printx * height , width, height);
                    printy++;
                }
                printx++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
