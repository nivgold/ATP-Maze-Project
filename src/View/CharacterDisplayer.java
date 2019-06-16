package View;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CharacterDisplayer extends Canvas {

    private int characterRow;
    private int characterColumn;
    private KeyCode movement;
    public String characterName;
    public DoubleProperty cellHeight = new SimpleDoubleProperty(1);
    public DoubleProperty cellWidth = new SimpleDoubleProperty(1);


    public void setPosition(int row, int column){
        this.characterRow=row;
        this.characterColumn=column;
    }

    public void setCharacter(int row, int column, String characterName) {
        characterRow = row;
        characterColumn = column;
        this.characterName = characterName;
        movement = KeyCode.NUMPAD2;
        drawCharacter(KeyCode.NUMPAD2);
    }

    public void drawCharacter(){
        drawCharacter(movement);
    }

    public void drawCharacter(KeyCode movement) {
        this.movement=movement;
        String path=characterName;
        try {
            switch (movement){
                case NUMPAD8:
                    path+="\\back.png";
                    break;
                case NUMPAD9:
                    path+="\\rightup.png";
                    break;
                case NUMPAD6:
                    path+="\\right.png";
                    break;
                case NUMPAD3:
                    path+="\\rightdown.png";
                    break;
                case NUMPAD2:
                    path+="\\front.png";
                    break;
                case NUMPAD1:
                    path+="\\leftdown.png";
                    break;
                case NUMPAD4:
                    path+="\\left.png";
                    break;
                case NUMPAD7:
                    path+="\\leftup.png";
                    break;
            }
            Image characterImage = new Image(new FileInputStream(path));

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0,2000,2000);
            this.toFront();
            //Draw Character
            //gc.setFill(Color.RED);
            //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
            gc.drawImage(characterImage, characterColumn * cellWidth.getValue(), characterRow * cellHeight.getValue(), cellWidth.getValue(), cellHeight.getValue());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
