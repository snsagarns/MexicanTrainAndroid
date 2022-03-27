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
 * Player class, all utilities function
 * Instantiate game details, updates them and make necessary changes, sets winners, provides option for new game and so on
 * Author: Sagar Neupane
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */

public class Player {
    private Hand hand;
    private boolean drawn;
    private int selectedTileIndex;

    /**
     * Player:: Default constructor, initialize the hand
     * @param
     * @return none
     * @date 12/08/2021
     */
    public Player(){
        drawn = false;
        hand = new Hand();
        selectedTileIndex = -1;
    }

    // setter for the selected tile's index
    public void setSelectedTileIndex(int selectedTileIndex) {
        this.selectedTileIndex = selectedTileIndex;
    }
    //getter for the selected tile's index
    public int getSelectedTileIndex() {
        return selectedTileIndex;
    }

    /**
     * Player::getHand, get Hand tiles for players
     * @param
     * @return none
     * @date 12/08/2021
     */
    public Hand getHand() {
        return hand;
    }
    // getter for whether or not tile is drawn
    public boolean getDrawn(){return  drawn; }

    // setter for tile drawn
    public  void setDrawn(boolean drawn){
        this.drawn = drawn;
    }

    /**
     * Player::setHand,  To set the player's hand tiles
     * @param, Handobject
     * @return none
     * @date 12/08/2021
     */
    public void setHand(Hand h)
    {
        this.hand = h;
    }

    /**
     * Player:: addTilesToHand, Utility function to add tiles to the player's hand
     * @param, Tileobject
     * @return none
     * @date 12/08/2021
     */
    public void addTilesToHand(Tiles t)
    {
        this.hand.addTiles(t);
    }

    /**
     * Player:: deleteTilesFromHand, Utility function to delete the tiles from the player's hand from given index position
     * @param index of tile to be deleted
     * @return none
     * @date 12/08/2021
     */
    public void deleteTilesFromHand(int index)
    {
        this.hand.removeTile(index);
    }
    /**
     * Player:: isHandEmpty(),Utility function to check if the player's hand is exhausted, i.e, has no more tiles to play
     * @param
     * @return boolean value whether the hand is empty or not (true if it is, false if it's not)
     * @date 12/08/2021
     */
    public boolean isHandEmpty()
    {
        return this.hand.getHandTiles().isEmpty();
    }

    /**
     * Player:: selectTiles
     * To find out the index of tiles in hand required for playing on eligible train
     * To see if drawing a tile from the boneyard is necessary or not
     * @param
     * @return index or move code -1 if not playable
     * @date 12/08/2021
     */
    public int selectTiles(int needed_side) {
        // move -1 no playable tile in hand, else store index of playable tile in hand
        int move = -1;

        // tile object
        Tiles t;

        t = getDemandedTile(selectedTileIndex);

        if ((t.getFirstSide() == needed_side) || (t.getSecondSide() == needed_side))
        {
            move = validateSides(t.getFirstSide(), t.getSecondSide());
        }
        return move;
    }

    /**
     * Player::getDemandedTile,return the tile at given index
     * @param index
     * @return tile object t
     * @date 12/08/2021
     */
    public Tiles getDemandedTile(int index)
    {
        Tiles t;
        t = hand.getHandTiles().get(index);
        return t;
    }

    /**
     * Player::provideHelp, to help with the user with index of tile if they have already choosen train
     * @param  needed_side, side required for playing
     * @return message
     * @date 12/08/2021
     */
    public String provideHelp(int needed_side)
    {
        String message;
        int command_code = findGoodTile(needed_side);
        if (command_code >= 0)
        {
            message = "Computer suggests you to play tiles at index: " + (command_code + 1) +" because it is either a double tile or has got high matching pip";
            message += hand.getHandTiles().get(command_code).printTiles();
        }
        else
        {
            message = "No tiles playable in the train you selected. Draw a tile";
        }
        return message;
    }

    /**
     * Player::findGoodTile, to find the index of the most suitable tile to play in order to maximize chances of winning
     * @param:  needed_side, side required for playing
     * @return index of tile
     * @date 12/08/2021
     */
    public int findGoodTile(int side)
    {

        int count = 0;
        int returnCode=-1;
        int temp = -1;
        int tempSum = -1;

        for(Tiles t: hand.getHandTiles())
        {
            if ((t.getFirstSide() == side) && (t.getSecondSide() == side))
            {

                return hand.findSidesInTile(side, side);
            }
            // if not found, continue the iteration to find tile with side with maximum sum combination with other half
            else
            {
                if ((t.getFirstSide() == side) || (t.getSecondSide() == side))
                {
                    tempSum = (t.getFirstSide() * 10 + t.getSecondSide());
                    if (tempSum > temp)
                    {
                        temp = tempSum;
                        returnCode = count;
                    }
                }
            }
            count++;
        }
        return returnCode;
    }

    /**
     * Player::validateSides, To check if the two sides are valid are pip value of valid tile or not
     * @param:  side1 and side 2: first and second side
     * @return index of tile
     * @date 12/08/2021
     */
    public int validateSides(int side1, int side2)
    {
        int count = 0;
        for(Tiles t: hand.getHandTiles())
        {
            if ((t.getFirstSide() == side1) && (t.getSecondSide() == side2))
            {
                return count;
            }
            count++;
        }
        return -1;
    }

    /**
     * Player::getSumofHands, to find the sum of all tiles existing in the player's hand
     * @param:
     * @return sum, sum of tiles sides
     * @date 12/08/2021
     */
    public int getSumofHands()
    {
        int sum = 0;
        for(Tiles t: hand.getHandTiles())
        {
            sum = sum + t.getSumOfSides();
        }
        return sum;
    }
    /**
     * Player::calculateScore, to calculate the score of the player
     * @param:
     * @return sum of all tiles left in the player's hand
     * @date 12/08/2021
     */
    public int calculateScore()
    {
        return this.getSumofHands();
    }
}
