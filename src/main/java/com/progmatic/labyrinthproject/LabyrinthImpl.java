package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private CellType [][] labyrinth;
    private Coordinate playerPosition;
    
    public LabyrinthImpl() {
    }
    
    

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            labyrinth = new CellType[height][width];
            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            labyrinth[hh][ww] = CellType.WALL;
                            break;
                        case 'E':
                            labyrinth[hh][ww] = CellType.END;
                            break;
                        case 'S':
                            labyrinth[hh][ww] = CellType.START;
                            break;
                    }
                }
            }
            
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    
    @Override
    public int getWidth() {
        if (labyrinth == null) {
           return -1;
       } else {
           return labyrinth.length;
       }
        
    }

    @Override
    public int getHeight() {
        if (labyrinth == null) {
           return -1;
       } else {
           return labyrinth.length;
       }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if(c.getRow() >= labyrinth.length || c.getRow() < 0 || c.getCol() >= labyrinth.length || c.getCol()<0){
            throw new CellException(c, "Cell is not in the board");
        }
        return labyrinth[c.getRow()][c.getCol()];
    }
    
    

    @Override
    public void setSize(int width, int height) {
        CellType [][] newLabyrinth = new CellType[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newLabyrinth[i][j] = CellType.EMPTY;
           
            }  
        }
        labyrinth= newLabyrinth;
    }
      

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        
        if (type == CellType.START) {
            playerPosition = c;
        }
        labyrinth[c.getRow()][c.getCol()] = type;
        
        if(c.getRow() > labyrinth.length || c.getRow() < 0 || c.getCol() > labyrinth.length || c.getCol()<0){
            throw new CellException(c, "Cell is not exist");
        }
    }
        

    @Override
    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() {
        if(labyrinth[playerPosition.getRow()][playerPosition.getCol()] == CellType.END){
            return true;
        }
        return false;
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> directions = new ArrayList<>();
        if(labyrinth[playerPosition.getRow()-1][playerPosition.getCol()] != CellType.WALL){
            directions.add(Direction.NORTH);
        }
        if(labyrinth[playerPosition.getRow()+1][playerPosition.getCol()] != CellType.WALL){
            directions.add(Direction.SOUTH);
        }               
        if(labyrinth[playerPosition.getRow()][playerPosition.getCol()-1] != CellType.WALL){
            directions.add(Direction.WEST);
        }
        if(labyrinth[playerPosition.getRow()][playerPosition.getCol()+1] != CellType.WALL){
            directions.add(Direction.EAST);
        }
        
        return directions;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        switch (direction){
            case NORTH:
                if(playerPosition.getRow()-1 < 0l || labyrinth[playerPosition.getRow()-1][playerPosition.getCol()] == CellType.WALL){
                    throw new InvalidMoveException();
                } else {
                     playerPosition = new Coordinate(playerPosition.getCol(), playerPosition.getRow()-1);
                }
                break;
            case SOUTH:
                if(labyrinth[playerPosition.getRow()+1][playerPosition.getCol()] == CellType.WALL){
                    throw new InvalidMoveException();
                } else {
                    playerPosition = new Coordinate(playerPosition.getCol(), playerPosition.getRow()+1);
                }
                break;
            case WEST:
                if(playerPosition.getCol()-1 < 0 || labyrinth[playerPosition.getRow()][playerPosition.getCol()-1] == CellType.WALL){
                    throw new InvalidMoveException();
                } else {
                    playerPosition = new Coordinate(playerPosition.getCol()-1, playerPosition.getRow());
                }
                break;
            case EAST:
                if(labyrinth[playerPosition.getRow()][playerPosition.getCol()+1] == CellType.WALL){
                    throw new InvalidMoveException();
                } else {
                    playerPosition = new Coordinate( playerPosition.getCol()+1, playerPosition.getRow());
                }
                break;
        }
            
    }

}
