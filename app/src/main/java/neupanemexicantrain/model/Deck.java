/*
 *****************************************************************
 * Name:         Sagar Neupane                                   *
 * Project:      1. Mexican Train Java Android                   *
 * Class:        CMPS 366, Organization of Programming Language  *
 * Date:         10/22/2021                                      *
 *****************************************************************
 */

package ramapo.edu.neupanemexicantrain.model;

import java.util.Collections;
import java.util.Vector;

/**
 * Deck Class
 * Creates all possible 55 tiles, shuffles the deck and extracts engine
 * Author: Sagar Neupane
 * Project: Mexican Train in Android
 * Class: CMPS 366
 * Last Modified on: 12/08/2021
 */
public class Deck {
    private Vector<Tiles> deck= new Vector<Tiles>();

    /**
     * Deck:: Default Constructor
     * To construct a Deck Object and intialize it with all possible tiles
     * @param none
     * @return none
     * @date 12/08/2021
     */
    public Deck()
    {
        for (int i =0; i <= 9; i++)
        {
            for (int j = i; j <= 9; j++)
            {
                this.deck.add(new Tiles(i, j));
            }
        }
    }
    //getter for the deck
    public Vector<Tiles> getDeck() { return this.deck; }

    // setter for the deck
    void setDeck(Vector<Tiles> t)
    {
        deck = t;
    }

    /**
     * Deck:: getEngineFromDeck
     * to extract the engine tile based on the round and set the engine accordingly for higher rounds
     * delete the engine tile from the deck after extracting it
     * @param round, round number
     * @return t, engine tile object
     * @date 12/08/2021
     */
    public Tiles getEngineFromDeck(int round)
    {
        // temp to store the engine pip value
        int engine_pip;

        // temp to receive and store the round number
        int temp = round;

        // stores the index of engine in the deck
        int engine_code = -1;

        // loop counter
        int index = 0;

        if (temp < 11)
        {
            temp = 10 - temp;
        }
        else
        {
            temp = 10 - temp % 10;
        }

        // storing double digit of engine pip
        temp = temp * 10 + temp;

        for (Tiles tile: deck){
            int value = tile.getDoubleDigitSides();

            if (value == temp)
            {
                engine_code = index;
            }
            index++;
        }

        // removes the engine tile from the deck
        deck.remove(engine_code);

        // creates a tile object with engine pip and return it
        engine_pip = temp % 10;

        // tile object to store the engine tile
        Tiles t = new Tiles(engine_pip,engine_pip);

        return t;
    }

    /**
     * Deck:: shuffleDeck: To shuffle a deck of cards
     * @param none
     * @return none
     * @date 12/08/2021
     */
    public void shuffleDeck()
    {
        Collections.shuffle(deck);
    }

    /**
     * Deck::  distributeToPlayer,  To provide unique 16 tiles to each player from the deck and delete them later
     * @param none
     * @return tile vector for player
     * @date 12/08/2021
     */
    public Vector<Tiles> distributeToPlayer()
    {

        // get first 16 tiles
        Vector<Tiles> tile_vec = new Vector<Tiles>(deck.subList(0,16));

        // after distributing delete those tiles
        for (int i = 0; i < 16; i++)
        {
            deck.remove(0);
        }

        return tile_vec;
    }

    /**
     * Deck:: getFromBoneyard, To extract the topmost tile from the boneyard
     * @param none
     * @return topmost tile from the boneyard
     * @date 12/08/2021
     */
    public Tiles getFromBoneyard()
    {
        Tiles temp = this.deck.get(0);
        deck.remove(0);
        return temp;
    }
    /**
     * Deck:: getSizeDeck, To get the size of deck, i.e, number of remaining tiles in the deck
     * @param none
     * @return size, size of the deck
     * @date 12/08/2021
     */
    public int getSizeDeck()
    {
        int size = getDeck().size();
        return size;
    }
}
