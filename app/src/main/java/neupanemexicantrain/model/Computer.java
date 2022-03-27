/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      1. Mexican Train Java Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         10/22/2021                                      *
 *****************************************************************
 */
package ramapo.edu.neupanemexicantrain.model;

/**
 * Computer Class
 * Implements Computer strategies to select the tiles.
 * Author: Sagar Neupane
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */

public class Computer extends Player {
    /**
     * Computer::selectTiles
     * To separate the user call for select Tiles from computer call and automate computer's play
     * User should be asked for input, but computer will play automatically
     * @param side, side needed to play
     * @return returns the index of the tile if possible to play, if not possible, returns -1
     */
    public int selectTiles(int side){
        int move = -1;

        int command_code = findGoodTile(side);

        if (command_code == -1)
        {
            return -1;
        }
        else
        {
            Tiles t = getDemandedTile(command_code);

            move = validateSides(t.getFirstSide(), t.getSecondSide());
            return move;
        }
    }
}
