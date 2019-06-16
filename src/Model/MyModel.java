package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;

public class MyModel extends Observable implements IModel{


    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer;

    private Maze maze;
    private Solution solution;

    private int characterRow;
    private int characterColumn;

    public MyModel(){
        startServers();
        this.characterRow = 0;
        this.characterColumn = 0;
    }

    public void resetPlayerPosition(){
        this.characterRow=0;
        this.characterColumn=0;
    }

    private void startServers() {
        this.mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        this.solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        this.maze=null;
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }
    public void stopServers(){
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }

    @Override
    public void generateMaze(int height, int width) {
        try{
            Client client = new Client (InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{height, width};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[12+(height*width)];
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }){
            };
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(maze);
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void moveCharacter(KeyCode movement) {
        switch (movement) {
            case NUMPAD8:
                if(maze.validPosition(new Position(characterRow-1,characterColumn))&&!maze.isWall(new Position(characterRow-1,characterColumn))) {
                    characterRow--;
                    setChanged();
                    notifyObservers(movement);
                }
                break;
            case NUMPAD2:
                if(maze.validPosition(new Position(characterRow+1,characterColumn))&&!maze.isWall(new Position(characterRow+1,characterColumn))) {
                    characterRow++;
                    setChanged();
                    notifyObservers(movement);
                }
                break;
            case NUMPAD6:
                if(maze.validPosition(new Position(characterRow,characterColumn+1))&&!maze.isWall(new Position(characterRow,characterColumn+1))) {
                    characterColumn++;
                    setChanged();
                    notifyObservers(movement);
                }
                break;
            case NUMPAD7:
                if(maze.validPosition(new Position(characterRow-1,characterColumn-1))&&!maze.isWall(new Position(characterRow-1,characterColumn-1))){
                    if(!maze.isWall(new Position(characterRow,characterColumn-1)) || !maze.isWall(new Position(characterRow-1,characterColumn))) {
                        characterRow--;
                        characterColumn--;
                        setChanged();
                        notifyObservers(movement);
                    }
                }
                break;
            case NUMPAD9:
                if(maze.validPosition(new Position(characterRow-1,characterColumn+1))&&!maze.isWall(new Position(characterRow-1,characterColumn+1))){
                    if(!maze.isWall(new Position(characterRow,characterColumn+1)) || !maze.isWall(new Position(characterRow-1,characterColumn))) {
                        characterRow--;
                        characterColumn++;
                        setChanged();
                        notifyObservers(movement);
                    }
                }
                break;
            case NUMPAD3:
                if(maze.validPosition(new Position(characterRow+1,characterColumn+1))&&!maze.isWall(new Position(characterRow+1,characterColumn+1))){
                    if(!maze.isWall(new Position(characterRow,characterColumn+1)) || !maze.isWall(new Position(characterRow+1,characterColumn))) {
                        characterRow++;
                        characterColumn++;
                        setChanged();
                        notifyObservers(movement);
                    }
                }
                break;
            case NUMPAD1:
                if(maze.validPosition(new Position(characterRow+1,characterColumn-1))&&!maze.isWall(new Position(characterRow+1,characterColumn-1))){
                    if(!maze.isWall(new Position(characterRow,characterColumn-1)) || !maze.isWall(new Position(characterRow+1,characterColumn))) {
                        characterRow++;
                        characterColumn--;
                        setChanged();
                        notifyObservers(movement);
                    }
                }
                break;
            case NUMPAD4:
                if(maze.validPosition(new Position(characterRow,characterColumn-1))&&!maze.isWall(new Position(characterRow,characterColumn-1))) {
                    characterColumn--;
                    setChanged();
                    notifyObservers(movement);
                }
                break;
        }
        if(characterRow==maze.getGoalPosition().getRowIndex() && characterColumn==maze.getGoalPosition().getColumnIndex()){
            setChanged();
            notifyObservers(new Integer(1));
        }
    }

    @Override
    public int getCharacterPositionRow() {
        return characterRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterColumn;
    }

    @Override
    public void setMaze(Maze maze) {
        this.maze=maze;
    }

    @Override
    public void setCharacterPositionRow(int row) {
        this.characterRow = row;
    }

    @Override
    public void setCharacterPositionColumn(int column) {
        this.characterColumn = column;
    }

    @Override
    public void solveMaze() {
        try{
            Client client = new Client (InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.writeObject(maze);
                        toServer.flush();
                        solution = (Solution) fromServer.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }){
            };
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(solution);
    }

    @Override
    public Solution getSolution() {
        return solution;
    }
}
