package ramapo.edu.neupanemexicantrain.model;

import java.io.Serializable;

/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      3. Mexican Train Java/Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         10/22/2021                                      *
 *****************************************************************
 */

/**
 * Tile class
 * Implemented for basic tile manipulation and printing as an information
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class Tiles implements Serializable {
    // stores the first side of the tile
   private int first_side;

    // stores the second side of the tiles
   private int second_side;

    // boolean variable to check if the tile's first side and second side are same.
    private  boolean is_double;

    // boolean variable to check if the tile's should be reversed to fit the train
    private boolean is_reverse;

    // getter for the first side of the tile
    public int getFirstSide() {
        return first_side;
    }

    // getter for the second side of the tile
    public int getSecondSide() {
        return second_side;
    }

    // Accessors
    public boolean checkDouble() { return this.is_double; }
    public boolean checkReverse() { return this.is_reverse; }

    /**
     * Tile class: Default constructor
     * @param
     * @return
     * @date 12/08/2021
     */
    public Tiles(){}

    /**
     * Tile class: Parameterized constructor to create a tile object, intialize is_reverse and set is_double
     * @param first_side, first pip of tile
     * @param  second_side, second side of the tile
     * @return
     * @date 12/08/2021
     */
    public Tiles(int first_side, int second_side){
        this.is_reverse = false;
        this.first_side = first_side;
        this.second_side = second_side;
        if(this.first_side == this.second_side){
            this.is_double = true;
        }else {
            this.is_double = false;
        }
    }

    /**
     * Tiles:: setSides  To set the first and second side of the tile;
     * @param  first_side,  an integer passed by value that is to be set as the first side of the tile
     * @param  second_side, an integer passed by value that is to be set as the second side of the tile
     * @date 12/08/2021
     */
    public void setSides(int first_side, int second_side){
        this.first_side = first_side;
        this.second_side = second_side;
    }

    /**
     * Tiles:: getSumOfSides To get the sum of two sides of tile which will be later helpful to select good tiles among matching tiles
     * @param
     * @return sum of sides
     * @date 12/08/2021
     */
    public int getSumOfSides(){
        return  this.first_side+this.second_side;
    }

    /**
     * Tiles:: getDoubleDigitSides To get the double digit value for tile manipulation. For instance 7-3 gives 73
     * @param
     * @return the double digit value from two sides of the tile
     * @date 12/08/2021
     */
    public int getDoubleDigitSides(){
        return this.first_side*10 + this.second_side;
    }

    /**
     * Tiles:: reverseTile To set the boolean reverse_tile variable when needed.
     * @param
     * @return the double digit value from two sides of the tile
     * @date 12/08/2021
     */
    public void reverseTile(){
        this.is_reverse = true;
    }

    /**
     * Tiles:: printTiles To format the tile and print it in information or pass as a string later
     * @param
     * @return string of tile being printed
     * @date 12/08/2021
     */
/* *********************************************************************
Function Name: printTiles
Purpose:
        To format the tile and print it
Parameters: None
Return Value: None
Algorithm:
        checks if the tile's sides need to be reversed. If yes, then print the second side of the tile first
        followed by the first side. If not, then print the first side followed by second side
Assistance Received: None
********************************************************************* */

    public String printTiles(){
        if(!is_reverse){
            return "|"+this.first_side+"-"+this.second_side+"|";
        }
        else{
            return "|"+this.second_side+"-"+this.first_side+"|";
        }
    }
}
