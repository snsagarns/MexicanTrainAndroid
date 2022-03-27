/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      1. Mexican Train Java Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         10/22/2021                                      *
 *****************************************************************
 */

package ramapo.edu.neupanemexicantrain.model;
import java.util.Vector;

/**
 * Hand Class
 * initializes and deals with the hand for each players, contains utility functions
 * Author: Sagar Neupane
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class Hand {
    private Vector<Tiles> tilesIn_hand;

      enum tileCode {
          double_tile(-2),
          not_found(-1);

          private int value;

          private tileCode(int value){
              this.value = value;
          }
      }
    /**
     * Hand:: Default Constructor
     * @param none
     * @return none
     * @date 12/08/2021
     */
    public  Hand(){}

    /**
     * Hand:: Parameterized constructor to set the hand tiles vector with the tiles provided
     * @param none
     * @return none
     * @date 12/08/2021
     */
    public Hand(Vector<Tiles> my_tiles){
          this.tilesIn_hand = my_tiles;
      }

    // getter function
    public Vector<Tiles> getHandTiles(){
        return  this.tilesIn_hand;
    }

    // setter function
    public void setHandTiles(Vector<Tiles> tiles){
        this.tilesIn_hand = tiles;
    }

    // utility function to get hand size
    public int getHandSize(){
        return this.tilesIn_hand.size();
    }

    // utility function to add tiles in hand
    public void addTiles(Tiles tile){
        this.tilesIn_hand.add(tile);
    }

    /**
     * Hand:: removeTile: Remove the hand tile from the provided indexs
     * @param index
     * @return none
     * @date 12/08/2021
     */
    public void removeTile(int index)
    {
        this.tilesIn_hand.remove( index);
    }

    /**
     * Hand:: getTile: To extract the tile from provied index
     * @param index, index of tile to be extracted
     * @return t, tile from the given index
     * @date 12/08/2021
     */
    public Tiles getTile(int index)
    {
        // tile object
        Tiles t;
        t = tilesIn_hand.get(index);
        return t;
    }

    /**
     * Hand:: findSideInTile: check if the player's hand has a tile with given side and get index if it has
     * @param side
     * @return tile_code, returns the index of tile with a given side if found, if not found return not_found, i.e, -1
     * @date 12/08/2021
     */
    public int findSideInTile(int side)
    {
        // store the index of a required tile, counter variable
        int index = 0;

        // stores the appropriate tile code
        int tile_code = tileCode.not_found.value;

        for(Tiles t: tilesIn_hand)
        {
            int value = t.getDoubleDigitSides();

            if ((value / 10) == side || (value % 10) == side)
            {
                tile_code = index;
                break;
            }
            else if ((value / 10) == side && (value % 10) == side)
            {
                tile_code = tileCode.double_tile.value;
            }
            index++;
        }
        return tile_code;
    }
    /**
     * Hand:: findSidesInTile: check if the player's hand has a tile with given sides and get index if it has
     * @param side
     * @return tile_code, returns the index of tile with a given side if found, if not found return not_found, i.e, -1
     * @date 12/08/2021
     */
   public  int findSidesInTile(int side_one, int side_two)
    {
        int index = 0;
        int tile_code = tileCode.not_found.value;

        for(Tiles t: tilesIn_hand)
        {
            int value = t.getDoubleDigitSides();

            if ((value / 10) == side_one && (value % 10) == side_two)
            {
                tile_code = index;
                return tile_code;
            }
            index++;
        }
        return tileCode.not_found.value;
    }
}
